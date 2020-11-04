package com.ali.retail.prism_r;

import cnm.hither.search.prism.PrismSystem;
import cnm.hither.search.prism.conf.PrismConf;
import cnm.hither.search.prism.query.DocMatched;
import com.ali.retail.bean.DataBeanParent;
import com.ali.retail.config.RedisConfig;
import com.ali.retail.prism.MysqlUtil;
import com.zhongsou.search.core.article.Article;
import com.zhongsou.search.core.query.Term;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PrismUtil2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrismUtil2.class);
    private static StringRedisTemplate redisTemplate = RedisConfig.stringRedisTemplate();
    private static Map<Integer, String> idMapping = new HashMap<>();
    private static final String PRISM_INIT_LOCK_KEY = "PrismDBInitRLockKey";
    private static final String PRISM_INIT_LOCK_VALUE = "PrismDBInitRLockValue";
    private static final String PRISM_UPDATE_LOCK_KEY = "PrismUpdateRLockKey";
    private static final String PRISM_UPDATE_LOCK_VALUE = "PrismUpdateRLockValue";

    //初始化prism连接实例，并初始化prism库
    public static void init() {
        try {
            PrismConf cfg = new PrismConf();
            PrismSystem.init(cfg);
            redisTemplate.delete(PRISM_INIT_LOCK_KEY);//TODO 是否需要
            addTerms();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将前端库专题数据灌入prism，同时更新映射关系表(支持批量)
     * @throws Exception
     */
    public static void addTerms() throws Exception{
        if (redisTemplate.opsForValue().setIfAbsent(PRISM_INIT_LOCK_KEY, PRISM_INIT_LOCK_VALUE)) {
            redisTemplate.expire(PRISM_INIT_LOCK_KEY, 30, TimeUnit.MINUTES);
            LOGGER.info("Begin to init prism db now.");
            PrismSystem ps = PrismSystem.getInstance();
            changeToEditMode(ps);
            LOGGER.info("clearAllTerms:" + ps.clearAllTerms());
            //获取前端专题数据
            Map<String, Set<String>> wordSubjectIds = MysqlUtil.getV_SubjectByType(MysqlUtil.getConnection(), 3);
            Set<String> keywords = wordSubjectIds.keySet();
            Iterator<String> iterator = keywords.iterator();
            LOGGER.info("Prism begin to add terms. Subject size: " + wordSubjectIds.size());
            Term term;
            while (iterator.hasNext()) {
                term = new Term(ps, Term.Sorter.date, Term.Counter.no);
                String key = iterator.next();
                String keyword = to_search_str(key);
                try {
                    term.andText("TX", keyword);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                term.setStart(0);
                term.setAskNum(10);
                int id_Prism = ps.addTerm(term);
                idMapping.put(id_Prism, wordSubjectIds.get(key).toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", ""));
                LOGGER.info("@idMapping {id_Prism:" + id_Prism + ", id_subject:" +  wordSubjectIds.get(key) + "} -- keyword: " + keyword +"}");
            }
            changeToWorkMode(ps);
            LOGGER.info("Total term size: " + ps.getTotalTerms());
        } else {
            LOGGER.info("The work of init prism db has been down by other node.");
        }
    }

    /**
     * 触发的更新操作：根据文章标题与正文同步更新prism和映射关系表
     * @param keywords 专题关键词
     * @param id 专题ID
     * @throws Exception
     */
    public static void addTerm(String keywords, String id) throws Exception {
        if (redisTemplate.opsForValue().setIfAbsent(PRISM_UPDATE_LOCK_KEY, PRISM_UPDATE_LOCK_VALUE)) {
            redisTemplate.expire(PRISM_UPDATE_LOCK_KEY, 1, TimeUnit.SECONDS);
            if (StringUtils.isBlank(keywords)) return;
            LOGGER.info("Begin to update prism now.");
            PrismSystem ps = PrismSystem.getInstance();
            changeToEditMode(ps);
            Term term = new Term(ps, Term.Sorter.date, Term.Counter.no);
            String termWords = to_search_str(keywords);
            try {
                term.andText("TX", termWords);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            term.setStart(0);
            term.setAskNum(10);
            //更新prism库
            int id_Prism = ps.addTerm(term);
            //更新映射关系
            idMapping.put(id_Prism, id);
            LOGGER.info("@idMapping {id_Prism:" + id_Prism + ", id_subject:" + id + "} -- keyword: " + keywords +"}");
            changeToWorkMode(ps);
            LOGGER.info("Total term size: " + ps.getTotalTerms());
            redisTemplate.delete(PRISM_UPDATE_LOCK_KEY);
        } else {
            LOGGER.info("There is someone(other thread) updating prism db now. Current thread will try to update prism again after sleep 100ms.");
            Thread.sleep(100);//会拖慢打标签速度
            addTerm(keywords, id);
        }
    }

    public static List<Set<String>> getWordMatchSubIds(List<? extends DataBeanParent> articles) {
        PrismSystem ps;
        List<Set<String>> SubIds = new ArrayList<>();
        long start = System.currentTimeMillis();
        try {
            ps = PrismSystem.getInstance();
            LOGGER.info("@Prism total size: " + ps.getTotalTerms());
            List<com.zhongsou.search.core.article.Article> zs_articles = makeZonSouArticle(ps, articles);//LOGGER.info("article:\n" + article);
            DocMatched<List<int[]>> listDocMatched = null;
            int waitModeChangeTimes = 1;
            int netTryTimes = 1;
            while(true) {
                try {
                    listDocMatched = ps.docRun(zs_articles);
                    int code = listDocMatched.getResult();
                    if (code == 0) { // 编辑模式且未报错，则正常跳出
                        LOGGER.info("@Prism mode: " + ps.getMode());
                        break;
                    } else if (code == 1){ //非工作模式，需要等待prism模式更新，当前更新Prism需要10-30秒
                        if (waitModeChangeTimes >= 50) {
                            break;
                        }
                        long sleep = 1000L;
                        LOGGER.info("@Prism mode: " + ps.getMode() + ".Try match @PrismID times: " + waitModeChangeTimes + ", it will try again after " + sleep + " ms.");
                        Thread.sleep(sleep);//TODO 随着专题数增加，更新prism耗时会更久，此处时间可能需要调整
                        waitModeChangeTimes++;
                    } else {
                        LOGGER.error("@Prism has a exception code: " + code);
                        break;
                    }
                } catch (IOException e) { // 网络问题，需要重试
                    if (netTryTimes >= 3) {
                        break;
                    }
                    LOGGER.warn("Try match @PrismID times: " + netTryTimes + ", occur a IOException: " + e.getMessage());
                    netTryTimes++;
                } catch (Exception e) {
                    LOGGER.error("Analysis articles with a problem. \n" + e.getMessage());
                }
            }
            if (listDocMatched == null) {
                LOGGER.error("Match @PrismID meet a question, plz check your error log.");
                return SubIds;
            }
            List<int[]> ids_Prisms = listDocMatched.getTermIDs();//命中的专题ID
            if (ids_Prisms.size() > 0) {
                for (int[] ids_Prism : ids_Prisms) {
                    Set<String> ids_subject = new HashSet<>();
                    //实时获取映射关系
                    if (null != ids_Prism && ids_Prism.length > 0) {
                        for (int id_Prism : ids_Prism) {
                            if(idMapping.containsKey(id_Prism)) {
                                String sub_ids = idMapping.get(id_Prism);
                                ids_subject.add(sub_ids);
                            }
                        }
                    }
                    LOGGER.info("ID article matched, @prism: " + Arrays.toString(ids_Prism) + " -- subject: " + Arrays.deepToString(ids_subject.toArray()));
                    SubIds.add(ids_subject);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Prism init failed!");
            e.printStackTrace();
        }
        LOGGER.info("Batch article size: " + articles.size() + ", detect tag took: " + (System.currentTimeMillis() - start) + "ms.");
        return SubIds;//命中的专题ID
    }

    /**
     * 将文章标题与正文封装为prism识别的Article对象
     * @param ps prism实例
     * @return
     */
    public static List<com.zhongsou.search.core.article.Article> makeZonSouArticle(PrismSystem ps, List<? extends DataBeanParent> articles) {
        List<com.zhongsou.search.core.article.Article> zs_articles = new ArrayList<>();
        for (DataBeanParent article : articles) {
            com.zhongsou.search.core.article.Article zs_article = new com.zhongsou.search.core.article.Article(ps);
            zs_article.setValue("TX", article.getText());
            zs_article.setValue("TI", article.getTitle());
//            LOGGER.info("article:\n" + article);
            zs_articles.add(zs_article);
        }
        return zs_articles;
    }
    /**
     * 将文章标题与正文封装为prism识别的Article对象
     * @param text 文章正文
     * @param title 文章标题
     * @return
     */
    public static Set<String> getWordMatchExpressionIds(String text, String title) {
        PrismSystem ps;
        Set<String> ids_subject = new HashSet<>();
        try {
            ps = PrismSystem.getInstance();
            LOGGER.info("@Prism total size: " + ps.getTotalTerms());
            Article article = putWbToArticle(ps, text, title);//LOGGER.info("article:\n" + article);
            DocMatched<int[]>  docMatched = null;
            int waitModeChangeTimes = 1;
            int netTryTimes = 1;
            while(true) {
                try {
                    docMatched = ps.docRun(article);
                    if (docMatched.getResult() == 0) // 编辑模式且未报错，则正常跳出
                        break;
                    else { //非工作模式，需要等待prism模式更新，当前更新Prism需要10-30秒
                        if (waitModeChangeTimes >= 10) {
                            break;
                        }
                        long sleep = 5000L;
                        LOGGER.info("Prism mode: " + ps.getMode() + ".Try match @PrismID times: " + waitModeChangeTimes + ", it will try again after " + sleep + " ms.");
                        Thread.sleep(5000);//TODO 随着专题数增加，更新prism耗时会更久，此处时间可能需要调整
                        waitModeChangeTimes++;
                    }
                } catch (IOException e) { // 网络问题，需要重试
                    netTryTimes++;
                    if (netTryTimes >= 3) {
                        break;
                    }
                    LOGGER.warn("Try match @PrismID times: " + netTryTimes + ", occur a IOException: " + e.getMessage());
                } catch (Exception e) {
                    LOGGER.error(e.getMessage() + "\nAnalysis article with a problem.\nTX: " + article.getString("\nTX: ") + article.getString("TI"));
                }
            }
            if (docMatched == null) {
                LOGGER.error("Match @PrismID meet a question, plz check your error log.");
                return ids_subject;
            }
            int[] ids_Prism = docMatched.getTermIDs();//命中的专题ID
            LOGGER.info("Length of @PrismId that article matched: " +  (ids_Prism != null ? ids_Prism.length : 0));
            //实时获取映射关系
            if (null != ids_Prism && ids_Prism.length > 0) {
                for (int id_Prism : ids_Prism) {
                    if(idMapping.containsKey(id_Prism)) {
                        String sub_ids = idMapping.get(id_Prism);
                        ids_subject.add(sub_ids);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Prism init failed!");
            e.printStackTrace();
        }
        LOGGER.info("Value of @SubjectId that article matched: " + Arrays.deepToString(ids_subject.toArray()));
        return ids_subject;
    }

    /**
     * 将文章标题与正文封装为prism识别的Article对象
     * @param ps prism实例
     * @param text 文章正文
     * @param title 文章标题
     * @return
     */
    public static Article putWbToArticle(PrismSystem ps, String text, String title) {
        Article article = new Article(ps);
        article.setValue("TX", text);
        article.setValue("TI", title);
        LOGGER.info("article:\n" + article);
        return article;
    }

    public static void changeToWorkMode(PrismSystem ps) throws Exception {
        for(int i=0; i<3 ; i++){
            boolean r = ps.changeToWorkMode();
            if(r){
                LOGGER.info("changeToWorkMode ok");
                break;
            } else
                LOGGER.info("changeToWorkMode blocked");
        }
        ps.whichMode();
        LOGGER.info("mode: " + ps.getMode());
    }

    private static void changeToEditMode(PrismSystem ps) throws Exception {
        String mode = ps.getMode();
        if ("WORK_MODE".equals(mode)) {
            for (int i = 0; i < 3; i++) {
                boolean r = ps.changeToEditMode();
                if (r) {
                    LOGGER.info("changeToEditMode ok");
                    break;
                } else
                    LOGGER.info("changeToEditMode blocked");
            }
        }
        ps.whichMode();
        LOGGER.info("mode: " + ps.getMode());
    }

    private static String to_search_str(String line) {
        StringBuffer strb=new StringBuffer();
        int lastL=0;
        int lastR=0;
        char lastC=0;
        boolean find=false;
        for(int i=0;i<line.length();i++) {
            char c=line.charAt(i);
            switch(c){
                case '(':
                    if(lastC=='*') find=true;
                    lastL=i;break;
                case ')':
                    if(find){
                        find=false;
                        strb.setCharAt(lastL, '{');
                        for(int j=lastL+1;j<i;j++){
                            if(strb.charAt(j)=='|')
                                strb.setCharAt(j, ',');
                        }
                        c='}';
                    }else
                        lastR=i;break;
                case '*': if(lastC==')'){
                    strb.setCharAt(lastR, '}');
                    strb.setCharAt(lastL, '{');
                    for(int j=lastL+1;j<lastR;j++){
                        if(strb.charAt(j)=='|')
                            strb.setCharAt(j, ',');
                    }
                }
                    break;
            }
            strb.append(c);
            lastC=c;
        }

        return strb.toString();
    }

}
