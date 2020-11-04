package com.ali.retail.bean;

import java.io.Serializable;

public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    public String id             ;//UUID
    public String mid            ;//主键ID
    public String uid            ;//账号uid
    public String author         ;//作者名
    public String authorDegree   ;//账号等级,根据粉丝数排
    public String authorType     ;//作者类型

    public String picture        ;//图片
    public String title          ;//文档标题
    public String summary        ;//文档摘要
    public String content        ;//文章内容
    public String category       ;//文档级别
    public String industry       ;//所属行业
    public String location       ;//文档所属地域
    public String url            ;//文档网址
    public String snapshot       ;//文档快照
    public String politicPosition;//政治立场

    public String emotion        ;//情感类型
    public String downloadType   ;//采集类型(微博/微信/...)
    public String articleType    ;//信息类型：新闻、论坛
    public String source         ;//发布客户端来源
    public String crawlerSiteId  ;//信息源id,crawler_site_id,属于ams字段
    public String forwardFromSite;//转发的站点id
    public String forwardFromUrl ;//转发的url
    public String midF           ;//原创标记
    public String midP           ;//微博转发标记

    public String probeTags      ;//重点关注, 人工推荐, 垃圾值等标签
    public String serviceSign    ;//内服标记(网民爆料、媒体首发报道、事件通报、媒体跟踪报道、网民评论、谣言类信息、评论性文章、辟谣通报)
    public String tagsGarbage    ;//是否为垃圾信息
    public String rubbish        ;//是否经过垃圾过滤
    public String mainPost       ;//是否是主贴:0,1
    public String mainPostUrl    ;//主贴ID:从贴则为空

    public String customWordCloud;//各组织下自定义关键词(或其ID)
    public String personName     ;//人名词云关键词词组

    //收藏(store)，分享(share)
    public String gradeAll       ;//热度
    public String fans           ;//粉丝数
    public String readCount      ;//阅读数
    public String commentCount   ;//评论数
    public String favorCount     ;//点赞数
    public String reportCount    ;//转发数

    public String createTime     ;//文档发布时间
    public String indexTime      ;//文档写入数据库时间
    public String updateTime     ;//更新时间
    public String crawlerTime    ;//采集时间

    public String monitorTag     ;//监测ID
    public String eventTag       ;//专题ID
    public String alertTag       ;//预警ID
    public String ministryTag    ;//部门ID
    public String regionTag      ;//区域ID
    public String contentTag     ;//内容类型ID
    public String regionOrgTag   ;//区域所属组织ID
    public String contentOrgTag  ;//内容所属组织ID
    public String ministryOrgTag ;//部门所在组织ID
    public String alertOrgTag    ;//预警所在组织ID
    public String monitorOrgTag  ;//监测所在组织ID
    public String eventOrgTag    ;//专题所在组织ID

    public String titleTop       ;//通过接口写入文档时赋值
    public String rePostDepth    ;//t_status_weibo微博独有，传播层级，舆情趋势分析
    public String domain         ;//暂无说明

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorDegree() {
        return authorDegree;
    }

    public void setAuthorDegree(String authorDegree) {
        this.authorDegree = authorDegree;
    }

    public String getAuthorType() {
        return authorType;
    }

    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getPoliticPosition() {
        return politicPosition;
    }

    public void setPoliticPosition(String politicPosition) {
        this.politicPosition = politicPosition;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCrawlerSiteId() {
        return crawlerSiteId;
    }

    public void setCrawlerSiteId(String crawlerSiteId) {
        this.crawlerSiteId = crawlerSiteId;
    }

    public String getForwardFromSite() {
        return forwardFromSite;
    }

    public void setForwardFromSite(String forwardFromSite) {
        this.forwardFromSite = forwardFromSite;
    }

    public String getForwardFromUrl() {
        return forwardFromUrl;
    }

    public void setForwardFromUrl(String forwardFromUrl) {
        this.forwardFromUrl = forwardFromUrl;
    }

    public String getMidF() {
        return midF;
    }

    public void setMidF(String midF) {
        this.midF = midF;
    }

    public String getMidP() {
        return midP;
    }

    public void setMidP(String midP) {
        this.midP = midP;
    }

    public String getProbeTags() {
        return probeTags;
    }

    public void setProbeTags(String probeTags) {
        this.probeTags = probeTags;
    }

    public String getServiceSign() {
        return serviceSign;
    }

    public void setServiceSign(String serviceSign) {
        this.serviceSign = serviceSign;
    }

    public String getTagsGarbage() {
        return tagsGarbage;
    }

    public void setTagsGarbage(String tagsGarbage) {
        this.tagsGarbage = tagsGarbage;
    }

    public String getRubbish() {
        return rubbish;
    }

    public void setRubbish(String rubbish) {
        this.rubbish = rubbish;
    }

    public String getMainPost() {
        return mainPost;
    }

    public void setMainPost(String mainPost) {
        this.mainPost = mainPost;
    }

    public String getMainPostUrl() {
        return mainPostUrl;
    }

    public void setMainPostUrl(String mainPostUrl) {
        this.mainPostUrl = mainPostUrl;
    }

    public String getCustomWordCloud() {
        return customWordCloud;
    }

    public void setCustomWordCloud(String customWordCloud) {
        this.customWordCloud = customWordCloud;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getGradeAll() {
        return gradeAll;
    }

    public void setGradeAll(String gradeAll) {
        this.gradeAll = gradeAll;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getFavorCount() {
        return favorCount;
    }

    public void setFavorCount(String favorCount) {
        this.favorCount = favorCount;
    }

    public String getReportCount() {
        return reportCount;
    }

    public void setReportCount(String reportCount) {
        this.reportCount = reportCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIndexTime() {
        return indexTime;
    }

    public void setIndexTime(String indexTime) {
        this.indexTime = indexTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCrawlerTime() {
        return crawlerTime;
    }

    public void setCrawlerTime(String crawlerTime) {
        this.crawlerTime = crawlerTime;
    }

    public String getMonitorTag() {
        return monitorTag;
    }

    public void setMonitorTag(String monitorTag) {
        this.monitorTag = monitorTag;
    }

    public String getEventTag() {
        return eventTag;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    public String getAlertTag() {
        return alertTag;
    }

    public void setAlertTag(String alertTag) {
        this.alertTag = alertTag;
    }

    public String getMinistryTag() {
        return ministryTag;
    }

    public void setMinistryTag(String ministryTag) {
        this.ministryTag = ministryTag;
    }

    public String getRegionTag() {
        return regionTag;
    }

    public void setRegionTag(String regionTag) {
        this.regionTag = regionTag;
    }

    public String getContentTag() {
        return contentTag;
    }

    public void setContentTag(String contentTag) {
        this.contentTag = contentTag;
    }

    public String getRegionOrgTag() {
        return regionOrgTag;
    }

    public void setRegionOrgTag(String regionOrgTag) {
        this.regionOrgTag = regionOrgTag;
    }

    public String getContentOrgTag() {
        return contentOrgTag;
    }

    public void setContentOrgTag(String contentOrgTag) {
        this.contentOrgTag = contentOrgTag;
    }

    public String getMinistryOrgTag() {
        return ministryOrgTag;
    }

    public void setMinistryOrgTag(String ministryOrgTag) {
        this.ministryOrgTag = ministryOrgTag;
    }

    public String getAlertOrgTag() {
        return alertOrgTag;
    }

    public void setAlertOrgTag(String alertOrgTag) {
        this.alertOrgTag = alertOrgTag;
    }

    public String getMonitorOrgTag() {
        return monitorOrgTag;
    }

    public void setMonitorOrgTag(String monitorOrgTag) {
        this.monitorOrgTag = monitorOrgTag;
    }

    public String getEventOrgTag() {
        return eventOrgTag;
    }

    public void setEventOrgTag(String eventOrgTag) {
        this.eventOrgTag = eventOrgTag;
    }

    public String getTitleTop() {
        return titleTop;
    }

    public void setTitleTop(String titleTop) {
        this.titleTop = titleTop;
    }

    public String getRePostDepth() {
        return rePostDepth;
    }

    public void setRePostDepth(String rePostDepth) {
        this.rePostDepth = rePostDepth;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", mid='" + mid + '\'' +
                ", uid='" + uid + '\'' +
                ", author='" + author + '\'' +
                ", authorDegree='" + authorDegree + '\'' +
                ", authorType='" + authorType + '\'' +
                ", picture='" + picture + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", industry='" + industry + '\'' +
                ", location='" + location + '\'' +
                ", url='" + url + '\'' +
                ", snapshot='" + snapshot + '\'' +
                ", politicPosition='" + politicPosition + '\'' +
                ", emotion='" + emotion + '\'' +
                ", downloadType='" + downloadType + '\'' +
                ", articleType='" + articleType + '\'' +
                ", source='" + source + '\'' +
                ", crawlerSiteId='" + crawlerSiteId + '\'' +
                ", forwardFromSite='" + forwardFromSite + '\'' +
                ", forwardFromUrl='" + forwardFromUrl + '\'' +
                ", midF='" + midF + '\'' +
                ", midP='" + midP + '\'' +
                ", probeTags='" + probeTags + '\'' +
                ", serviceSign='" + serviceSign + '\'' +
                ", tagsGarbage='" + tagsGarbage + '\'' +
                ", rubbish='" + rubbish + '\'' +
                ", mainPost='" + mainPost + '\'' +
                ", mainPostUrl='" + mainPostUrl + '\'' +
                ", customWordCloud='" + customWordCloud + '\'' +
                ", personName='" + personName + '\'' +
                ", gradeAll='" + gradeAll + '\'' +
                ", fans='" + fans + '\'' +
                ", readCount='" + readCount + '\'' +
                ", commentCount='" + commentCount + '\'' +
                ", favorCount='" + favorCount + '\'' +
                ", reportCount='" + reportCount + '\'' +
                ", createTime='" + createTime + '\'' +
                ", indexTime='" + indexTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", crawlerTime='" + crawlerTime + '\'' +
                ", monitorTag='" + monitorTag + '\'' +
                ", eventTag='" + eventTag + '\'' +
                ", alertTag='" + alertTag + '\'' +
                ", ministryTag='" + ministryTag + '\'' +
                ", regionTag='" + regionTag + '\'' +
                ", contentTag='" + contentTag + '\'' +
                ", regionOrgTag='" + regionOrgTag + '\'' +
                ", contentOrgTag='" + contentOrgTag + '\'' +
                ", ministryOrgTag='" + ministryOrgTag + '\'' +
                ", alertOrgTag='" + alertOrgTag + '\'' +
                ", monitorOrgTag='" + monitorOrgTag + '\'' +
                ", eventOrgTag='" + eventOrgTag + '\'' +
                ", titleTop='" + titleTop + '\'' +
                ", rePostDepth='" + rePostDepth + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
