package com.ali.retail.common.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void flapMapTest() {
        List<Integer> list = Stream.of(Arrays.asList(1, 2, 3, 4, 5, 6), Arrays.asList(8, 9, 10, 11, 12))
                .flatMap(test -> test.stream()).collect(Collectors.toList());

        for (int i = 0, length = list.size(); i < length; i++) {
            System.out.println(list.get(i));
        }

    }

    private static JSONArray makeCrawlArray() throws UnsupportedEncodingException {
        JSONArray array = new JSONArray();
        for (int i = 0; i<5; i++) {
            array.add(makeCrawl(i+ "", i%2));
        }
        return array;
    }

    private static JSONArray makeISearchArray() throws UnsupportedEncodingException {
        JSONArray array = new JSONArray();
        for (int i = 0; i<10; i++) {
            array.add(makeISearch(i+ "", i%3, i/3+ 1));
        }
        return array;
    }

    private static JSONObject makeCrawl(String uid, int police) {
        JSONObject obj = new JSONObject();
        obj.put("uid", uid);
        obj.put("police", police);
//        System.out.println("crawl info: " + obj);
        return obj;
    }

    private static JSONObject makeISearch(String uid, int favor, int forward) {
        JSONObject obj = new JSONObject();
        obj.put("uid", uid);
        obj.put("favor", favor);
        obj.put("forward", forward);
//        System.out.println("iSearch info: " + obj);
        return obj;
    }

    private static void merge() throws UnsupportedEncodingException {
        JSONArray array = new JSONArray();
        JSONArray array1 = makeCrawlArray();
        JSONArray array2 = makeISearchArray();
        Stream<Object> stream1 = array1.stream();
        Stream<Object> stream2 = array2.parallelStream();
        stream1.forEach(x -> System.out.println(Thread.currentThread().getName() + ": " +  x));
        stream2.forEach(x -> System.out.println(Thread.currentThread().getName() + ": " +  x));

    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        merge();

//        flapMapTest();

//        Stream.of("one", "two", "three", "four")
//                .filter(e -> e.length() > 3)
//                .peek(e -> System.out.println("Filtered value: " + e))
//                .map(String::toUpperCase)
//                .peek(e -> System.out.println("Mapped value: " + e))
//                .collect(Collectors.toList());
    }





}
