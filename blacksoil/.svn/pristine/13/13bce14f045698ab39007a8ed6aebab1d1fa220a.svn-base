package com.ali.retail.prism_r;

import cnm.hither.search.prism.PrismSystem;
import cnm.hither.search.prism.conf.PrismConf;
import cnm.hither.search.prism.net.PrismConn;
import cnm.hither.search.prism.query.DocMatched;
import com.ali.retail.bean.DataBeanParent;
import com.ali.retail.prism.MysqlUtil;
import com.zhongsou.common.ByteBuffer;
import com.zhongsou.common.VConvert;
import com.zhongsou.search.core.article.Article;
import com.zhongsou.search.core.net.control.AskQuery;
import com.zhongsou.search.core.query.Term;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class PrismUtil_r {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrismUtil_r.class);
    public static Map<String, Map<Integer, String>> mappingByType = new HashMap<>();
    public static final String ALARM_SEARCH_SYSTEM_NAME = "alarm_prism";
    public static final String MONITOR_SEARCH_SYSTEM_NAME = "monitor_prism";
    public static final String SUBJECT_SEARCH_SYSTEM_NAME = "subject_prism";
    public static final String MAPPING_TYPE_ALARM = "v_subject_0";
    public static final String MAPPING_TYPE_MONITOR = "v_subject_2";
    public static final String MAPPING_TYPE_SUBJECT = "v_subject_3";


    //初始化prism连接实例，并初始化prism库
    public static void init() {
        try {
            /* prism 初始化*/
            PrismSystemInitializer_r.init(ALARM_SEARCH_SYSTEM_NAME, "PrismSystem-alarm.conf");
//            PrismSystemInitializer.init(MONITOR_SEARCH_SYSTEM_NAME, "PrismSystem-monitor.conf");
//            PrismSystemInitializer.init(SUBJECT_SEARCH_SYSTEM_NAME, "PrismSystem-subject.conf");
            /*更新预警映射关系*/
            addTerms(ALARM_SEARCH_SYSTEM_NAME, MAPPING_TYPE_ALARM, MysqlUtil.getV_SubjectByType(MysqlUtil.getConnection(), 2));
            /*更新检测映射关系*/
//            addTerms(MONITOR_SEARCH_SYSTEM_NAME, MAPPING_TYPE_MONITOR, MysqlUtil.getV_SubjectByType(2));
//            /*更新专题映射关系*/
//            addTerms(SUBJECT_SEARCH_SYSTEM_NAME, MAPPING_TYPE_SUBJECT, MysqlUtil.getV_SubjectByType(3));

            LOGGER.info(ALARM_SEARCH_SYSTEM_NAME + " @@@: " + PrismSystemInitializer_r.getPrismSystem(ALARM_SEARCH_SYSTEM_NAME).getCfg().getProps().get("system.net.sock.ip"));
//            LOGGER.info(MONITOR_SEARCH_SYSTEM_NAME + " @@@: " + PrismSystemInitializer.getPrismSystem(MONITOR_SEARCH_SYSTEM_NAME).getCfg().getProps().get("system.net.sock.ip"));
//            LOGGER.info(SUBJECT_SEARCH_SYSTEM_NAME + " @@@: " + PrismSystemInitializer.getPrismSystem(SUBJECT_SEARCH_SYSTEM_NAME).getCfg().getProps().get("system.net.sock.ip"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkConnection(String configFileName) throws Exception {
        PrismConf cfg = new PrismConf(configFileName);
        PrismConn pc = new PrismConn(cfg);
        boolean isConnect = new AskQuery(pc).testConnection();
        if(isConnect) {
            LOGGER.info("Test Server Query Connection Successful .");
        } else {
            LOGGER.error("Test Server Query Connection failed! " + pc.getErrMsg());
        }
        return isConnect;
    }

    /**
     * 将前端库专题数据灌入prism，同时更新映射关系表(支持批量)
     * @param search_system_name prism实例名
     * @param mappingType 映射关系的key
     * @param v_subject v_subject：keyword-ids数据集
     * @throws Exception
     */
    public static void addTerms(String search_system_name, String mappingType, Map<String, Set<String>> v_subject) throws Exception{
        long start = System.currentTimeMillis();
        Map<Integer, String> idMapping = mappingByType.computeIfAbsent(mappingType, k -> new HashMap<>());
        LOGGER.info("Begin to init prism: " + search_system_name + ". MappingType: " + mappingType);
        PrismSystem ps = PrismSystemInitializer_r.getPrismSystem(search_system_name);
        changeToEditMode(ps);
        LOGGER.info("clearAllTerms:" + ps.clearAllTerms());
        Set<String> keywords = v_subject.keySet();
        Iterator<String> iterator = keywords.iterator();
        LOGGER.info("Prism begin to add terms. Subject size: " + v_subject.size());
        Term term;
        while (iterator.hasNext()) {
            term = new Term(ps, Term.Sorter.date, Term.Counter.no);
            String key = iterator.next();
            String keyword = to_search_str(key);
            try {
                term.andText("TX", keyword);
            } catch (Exception e) {
                LOGGER.error("msg: {}, subject ids: {}, keyword: {}", e.getMessage() , v_subject.get(key), key);
                continue;
            }
            term.setStart(0);
            term.setAskNum(10);
            int id_Prism = ps.addTerm(term);
            idMapping.put(id_Prism, v_subject.get(key).toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", ""));
            LOGGER.info("@idMapping {id_Prism:" + id_Prism + ", id_subject:" +  v_subject.get(key) + "} -- keyword: " + keyword +"}");
        }
        changeToWorkMode(ps);
        LOGGER.info("merged subject size: {}, Total term size: {}, took: {}", v_subject.size(), ps.getTotalTerms(), System.currentTimeMillis() - start);
    }

    /**
     * 单term更新
     * @param keywords 专题关键词
     * @param id 专题ID
     * @throws Exception
     */
    public static void addTerm(String search_system_name, String mappingType, String keywords, String id) throws Exception {
        Map<Integer, String> idMapping = mappingByType.computeIfAbsent(mappingType, k -> new HashMap<>());
        if (StringUtils.isBlank(keywords)) return;
        LOGGER.info("Begin to update prism now.");
        PrismSystem ps = PrismSystemInitializer_r.getPrismSystem(search_system_name);
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
    }

    /**
     * @param search_system_name  prism实例对象
     * @param mappingType  映射关系的key
     * @param articles
     * @return
     */
    public static List<Set<String>> getWordMatchSubIds(String search_system_name, String mappingType, List<? extends DataBeanParent> articles) {
        LOGGER.info("Begin to getWordMatchSubIds by prism: " + search_system_name + ". MappingType: " + mappingType);
        Map<Integer, String> idMapping = mappingByType.computeIfAbsent(mappingType, k -> new HashMap<>());
        PrismSystem ps;
        List<Set<String>> subIds = new ArrayList<>();
        if (articles == null) {
            LOGGER.warn("articles is null!");
            return subIds;
        }
        long start = System.currentTimeMillis();
        try {
            ps = PrismSystemInitializer_r.getPrismSystem(search_system_name);
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
                        makeLogOfErrorAnalysisArticle(e, zs_articles);
                        break;
                    }
                    LOGGER.warn("Try match @PrismID times: " + netTryTimes + ", occur a IOException: " + e.getMessage());
                    netTryTimes++;
                } catch (Exception e) {
                    makeLogOfErrorAnalysisArticle(e, zs_articles);
                    break;
                }
            }
            if (listDocMatched == null) {
                LOGGER.error("Match @PrismID meet a mistake, plz check your error log.");
                return subIds;
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
                    subIds.add(ids_subject);
                }
            }
        } catch (Exception e) {
            LOGGER.error("get prism_id meet a mistake, msg - {}", e.getMessage());
        }
        LOGGER.info("Batch article size: " + articles.size() + ", detect tag took: " + (System.currentTimeMillis() - start) + "ms.");
        return subIds;//命中的专题ID
    }

    private static void makeLogOfErrorAnalysisArticle(Exception e, List<Article> zs_articles) throws Exception {
        LOGGER.error("Analysis articles with a mistake. msg - {}" + e.getMessage());
        OutputStream out = new FileOutputStream("error" + System.currentTimeMillis() + ".dat");
        for(Article a: zs_articles){
            ByteBuffer bb = a.toByteBuffer();
            int len=bb.getUsed();
            out.write(VConvert.int2Bytes(len));
            out.write(bb.getBytes(),0, len);
        }
        out.close();
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
            zs_article.setValue("MI", article.getMid());
            zs_article.setValue("TX", article.getText());
            zs_article.setValue("TI", article.getTitle());
            zs_articles.add(zs_article);
        }
        return zs_articles;
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


    /**
     * example of simple use
     */
    public static void main(String[] args) throws Exception {
        /*更新预警映射关系*/
        addTerms(ALARM_SEARCH_SYSTEM_NAME, MAPPING_TYPE_ALARM, MysqlUtil.getV_SubjectByType(MysqlUtil.getConnection(), 0));
        /*更新检测映射关系*/
        addTerms(MONITOR_SEARCH_SYSTEM_NAME, MAPPING_TYPE_MONITOR, MysqlUtil.getV_SubjectByType(MysqlUtil.getConnection(), 2));
        /*更新专题映射关系*/
        addTerms(SUBJECT_SEARCH_SYSTEM_NAME, MAPPING_TYPE_SUBJECT, MysqlUtil.getV_SubjectByType(MysqlUtil.getConnection(), 3));


        List<DataBeanParent> docs = new ArrayList<>();
        //获取文章命中的专题id
        getWordMatchSubIds(SUBJECT_SEARCH_SYSTEM_NAME, MAPPING_TYPE_SUBJECT, docs);
        //获取文章命中的监测id
        getWordMatchSubIds(MONITOR_SEARCH_SYSTEM_NAME, MAPPING_TYPE_MONITOR, docs);
        //获取文章命中的预警id
        getWordMatchSubIds(ALARM_SEARCH_SYSTEM_NAME, MAPPING_TYPE_ALARM, docs);
    }

}
