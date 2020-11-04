package com.ali.retail.common.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CollectionTest {

    private static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
        }
    }
}
