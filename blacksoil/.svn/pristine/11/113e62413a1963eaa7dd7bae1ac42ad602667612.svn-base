package com.ali.retail.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Twin on 2017/12/5.
 */
public class DataBeanParent {

    public DataBeanParent() {
    }

    public DataBeanParent(String text, String title) {
        this.text = text;
        this.title = title;
    }

    protected String mid; // 微博消息id，文档唯一标示.
    protected String text; // 信息内容.
    protected String title; // 标题
    /**
     * UnitTags
     */
    protected String subjects_tag;// 监测单元ID
    protected String orgs_tag;// 组织

    protected String events_tag;// 事件
    protected String orgs_event_tag;

    protected String alerts_tag;// 预警
    protected String orgs_alert_tag;

    protected String content_tags;// 内容
    protected String content_orgs;

    protected String ministry_tags;// 行业
    protected String ministry_orgs;

    protected String region_tags;// 区域
    protected String region_orgs;
    @JSONField(name = "media_type")
    protected List<String> mediaType;

    // protected Integer reposts_count; // 转发数.
    protected Integer reports_count;
    protected Integer zans_count; // 点赞数
    protected Integer comments_count; // 评论数.
    private Integer read_count;// 阅读数
    // protected Integer zan_count;
    protected Integer grade_all; // 微博热度.
    private String crawler_site_id;
    private Integer article_type = 0;// 0:微博,1:新闻,2:微信,3:论坛,4:博客,5:报纸,6:视频,7:qq,8:跟帖,9:境外,10:twitter,11:新闻客户端
    private String uid; // 作者id (站点Id_官网id).

    protected List<String> tags;// 标签

    protected String[] keywords;// 提取的关键词
    protected String[] name_keywords;// 提取的关键词
    protected Long crawlerDiffTime;// 发布时间与采集时间差
    @JSONField(name = "dim_code")
    protected DimCode dimCode;


    private String title_top;

    protected Map<String, Long> feelings;

    public Map<String, Long> getFeelings() {
        return feelings;
    }

    public void setFeelings(Map<String, Long> feelings) {
        this.feelings = feelings;
    }

    public String getTitle_top() {
        return title_top;
    }

    public void setTitle_top(String title_top) {
        this.title_top = title_top;
    }

    public String[] getName_keywords() {
        return name_keywords;
    }

    public void setName_keywords(String[] name_keywords) {
        this.name_keywords = name_keywords;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getArticle_type() {
        return article_type;
    }

    public void setArticle_type(Integer article_type) {
        this.article_type = article_type;
    }

    public String getCrawler_site_id() {
        return crawler_site_id;
    }

    public void setCrawler_site_id(String crawler_site_id) {
        this.crawler_site_id = crawler_site_id;
    }


    /**
     * @return the dimCode
     */
    public DimCode getDimCode() {
        return dimCode;
    }

    /**
     * @param dimCode the dimCode to set
     */
    public void setDimCode(DimCode dimCode) {
        this.dimCode = dimCode;
    }

    public Long getCrawlerDiffTime() {
        return crawlerDiffTime;
    }

    public void setCrawlerDiffTime(Long crawlerDiffTime) {
        this.crawlerDiffTime = crawlerDiffTime;
    }

    Date indextime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    protected Timestamp created_at; // 创建时间，精确到秒（微博发布时间）.

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public List<String> getMediaType() {
        return mediaType;
    }

    public void setMediaType(List<String> mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getRead_count() {
        return read_count;
    }

    public void setRead_count(Integer read_count) {
        this.read_count = read_count;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getIndextime() {
        return indextime;
    }

    public void setIndextime(Date indextime) {
        this.indextime = indextime;
    }

    private List<String> source_tags;// 默认信息源分组

    public List<String> getSource_tags() {
        return source_tags;
    }

    public void setSource_tags(List<String> source_tags) {
        this.source_tags = source_tags;
    }

    public Integer getReports_count() {
        return reports_count;
    }

    public void setReports_count(Integer reports_count) {
        this.reports_count = reports_count;
    }

    public Integer getZans_count() {
        return zans_count;
    }

    public void setZans_count(Integer zans_count) {
        this.zans_count = zans_count;
    }

    public Integer getComments_count() {
        return comments_count;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
    }

    public Integer getGrade_all() {
        return grade_all;
    }

    public void setGrade_all(Integer grade_all) {
        this.grade_all = grade_all;
    }

    public String getSubjects_tag() {
        return subjects_tag;
    }

    public void setSubjects_tag(String subjects_tag) {
        this.subjects_tag = subjects_tag;
    }

    public String getOrgs_tag() {
        return orgs_tag;
    }

    public void setOrgs_tag(String orgs_tag) {
        this.orgs_tag = orgs_tag;
    }

    public String getEvents_tag() {
        return events_tag;
    }

    public void setEvents_tag(String events_tag) {
        this.events_tag = events_tag;
    }

    public String getOrgs_event_tag() {
        return orgs_event_tag;
    }

    public void setOrgs_event_tag(String orgs_event_tag) {
        this.orgs_event_tag = orgs_event_tag;
    }

    public String getAlerts_tag() {
        return alerts_tag;
    }

    public void setAlerts_tag(String alerts_tag) {
        this.alerts_tag = alerts_tag;
    }

    public String getOrgs_alert_tag() {
        return orgs_alert_tag;
    }

    public void setOrgs_alert_tag(String orgs_alert_tag) {
        this.orgs_alert_tag = orgs_alert_tag;
    }

    public String getContent_tags() {
        return content_tags;
    }

    public void setContent_tags(String content_tags) {
        this.content_tags = content_tags;
    }

    public String getContent_orgs() {
        return content_orgs;
    }

    public void setContent_orgs(String content_orgs) {
        this.content_orgs = content_orgs;
    }

    public String getMinistry_tags() {
        return ministry_tags;
    }

    public void setMinistry_tags(String ministry_tags) {
        this.ministry_tags = ministry_tags;
    }

    public String getMinistry_orgs() {
        return ministry_orgs;
    }

    public void setMinistry_orgs(String ministry_orgs) {
        this.ministry_orgs = ministry_orgs;
    }

    public String getRegion_tags() {
        return region_tags;
    }

    public void setRegion_tags(String region_tags) {
        this.region_tags = region_tags;
    }

    public String getRegion_orgs() {
        return region_orgs;
    }

    public void setRegion_orgs(String region_orgs) {
        this.region_orgs = region_orgs;
    }


    @Override
    public String toString() {
        return "DataBeanParent [" + (mid != null ? "mid=" + mid + ", " : "")
                + (text != null ? "text=" + text + ", " : "") + (title != null ? "title=" + title + ", " : "")
                + (subjects_tag != null ? "subjects_tag=" + subjects_tag + ", " : "")
                + (orgs_tag != null ? "orgs_tag=" + orgs_tag + ", " : "")
                + (events_tag != null ? "events_tag=" + events_tag + ", " : "")
                + (orgs_event_tag != null ? "orgs_event_tag=" + orgs_event_tag + ", " : "")
                + (alerts_tag != null ? "alerts_tag=" + alerts_tag + ", " : "")
                + (orgs_alert_tag != null ? "orgs_alert_tag=" + orgs_alert_tag + ", " : "")
                + (content_tags != null ? "content_tags=" + content_tags + ", " : "")
                + (content_orgs != null ? "content_orgs=" + content_orgs + ", " : "")
                + (ministry_tags != null ? "ministry_tags=" + ministry_tags + ", " : "")
                + (ministry_orgs != null ? "ministry_orgs=" + ministry_orgs + ", " : "")
                + (region_tags != null ? "region_tags=" + region_tags + ", " : "")
                + (region_orgs != null ? "region_orgs=" + region_orgs + ", " : "")
                + (mediaType != null ? "mediaType=" + mediaType + ", " : "")
                + (reports_count != null ? "reports_count=" + reports_count + ", " : "")
                + (zans_count != null ? "zans_count=" + zans_count + ", " : "")
                + (comments_count != null ? "comments_count=" + comments_count + ", " : "")
                + (read_count != null ? "read_count=" + read_count + ", " : "")
                + (grade_all != null ? "grade_all=" + grade_all + ", " : "")
                + (crawler_site_id != null ? "crawler_site_id=" + crawler_site_id + ", " : "")
                + (article_type != null ? "article_type=" + article_type + ", " : "")
                + (uid != null ? "uid=" + uid + ", " : "") + (tags != null ? "tags=" + tags + ", " : "")
                + (keywords != null ? "keywords=" + Arrays.toString(keywords) + ", " : "")
                + (name_keywords != null ? "name_keywords=" + Arrays.toString(name_keywords) + ", " : "")
                + (crawlerDiffTime != null ? "crawlerDiffTime=" + crawlerDiffTime + ", " : "")
                + (dimCode != null ? "dimCode=" + dimCode + ", " : "")
                + (indextime != null ? "indextime=" + indextime + ", " : "")
                + (created_at != null ? "created_at=" + created_at + ", " : "")
                + (source_tags != null ? "source_tags=" + source_tags : "") +
                (feelings != null ? "feeling=" + feelings.toString() : "") + "]";
    }

    public static void main(String[] args) {
        DataBeanParent data = new DataBeanParent();
        data.setDimCode(DimCode.instance().industry("111").area(".32323"));
        System.out.println(JSON.toJSONString(data));
    }

    public static class DimCode {
        String[] industry;
        String[] area;

        public DimCode industry(String... industry) {
            this.industry = industry;
            return this;
        }

        @JSONField(name = "industry")
        public String[] industry() {
            return industry;
        }

        public DimCode area(String... area) {
            this.area = area;
            return this;
        }

        @JSONField(name = "area")
        public String[] area() {
            return area;
        }

        public static DimCode instance() {
            return new DimCode();
        }
    }
}
