package com.ali.retail.prism;

import com.ali.retail.bean.DataBeanParent;

import java.util.*;

public class DetectUtil {

    public void detectTag(List<? extends DataBeanParent> articles) {
        if (articles != null && articles.size() > 0) {
            List<Set<String>> matchIds = PrismUtil.getWordMatchSubIds("ps", articles);
            if (matchIds != null && articles.size() > 0) {
                for (int i = 0; i < articles.size(); i++) {
                    Set<String> ids = matchIds.get(i);
                    if (ids != null && ids.size() > 0)
                        articles.get(i).setRegion_tags(toString(ids));
                }
            }
        }
    }

    public static String toString(Collection<?> collection) {
        return collection.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "");
    }

    public static void main(String[] args) {
        Set<String> s = new HashSet<>();
        s.add("aa");
        s.add("bb");
        List<String> l = new ArrayList<>();
        l.add("cc");
        l.add("dd");
        System.out.println(toString(l));
    }
}
