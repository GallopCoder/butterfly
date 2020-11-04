package com.ali.retail;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OtherTest {

    static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static void main(String[] args) {
//        System.out.println(DATE_FORMAT.format(new Timestamp(1585152000000L).toLocalDateTime()));
        cut(1924592063, 3938);
    }

    public static void cut(long var0, long var1) {
        System.out.println(var0/var1);
    }
    public static void test() {
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date().getTime());
    }

    public static void test1(){
        Map<String, String> map = new HashMap<>();

        System.out.println(map.containsKey("1") ? map.get("1") : "hi");
    }

}
