package com.ali.retail.prism;

import com.ali.retail.bean.DataBeanParent;
import com.ali.retail.bean.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrismTest {

    public static final String ALARM_SEARCH_SYSTEM_NAME = "alarm_prism";
    public static final String MONITOR_SEARCH_SYSTEM_NAME = "monitor_prism";
    public static final String SUBJECT_SEARCH_SYSTEM_NAME = "subject_prism";


    public void  prismTest() {
        try {
            /*更新预警映射关系*/
//            PrismUtil.addTerms(ALARM_SEARCH_SYSTEM_NAME, MysqlUtil.getV_SubjectByType(0));
//            /*更新检测映射关系*/
//            PrismUtil.addTerms(MONITOR_SEARCH_SYSTEM_NAME, MysqlUtil.getV_SubjectByType(2));
//            /*更新专题映射关系*/
//            PrismUtil.addTerms(SUBJECT_SEARCH_SYSTEM_NAME, MysqlUtil.getV_SubjectByType(3));
//
//
//            List<DataBeanParent> docs = new ArrayList<>();
//            //获取文章命中的专题id
//            PrismUtil.getStrMatchedIds(SUBJECT_SEARCH_SYSTEM_NAME, docs);
//            //获取文章命中的监测id
//            PrismUtil.getStrMatchedIds(MONITOR_SEARCH_SYSTEM_NAME, docs);
//            //获取文章命中的预警id
//            PrismUtil.getStrMatchedIds(ALARM_SEARCH_SYSTEM_NAME, docs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Document> testObject(List<Document> documents) {
        AtomicInteger i = new AtomicInteger();
        documents.forEach(x -> {
            x.setText(x.getText() + "abc" + i.getAndIncrement());
        });
        return documents;
    }


    /**
     * example of simple use
     */
    public static void main(String[] args) {

        MysqlUtil.getConnection();
//        List<Document> documents = new ArrayList<>();
//        documents.add(new Document("prism 1"));
//        documents.add(new Document("prism 2"));
//        documents.add(new Document("prism 3"));
//
//        testObject(documents);
//
//        System.out.println(documents);
    }
}
