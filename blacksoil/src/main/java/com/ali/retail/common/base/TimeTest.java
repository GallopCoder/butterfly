package com.ali.retail.common.base;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

public class TimeTest {

    private LocalDate ld = LocalDate.now();
    private LocalTime lt = LocalTime.now();

    public static void main(String[] args) {
        test2();

    }


    public static void test2() {
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);//当天零点
        long timestamp = Timestamp.valueOf(today_start).getTime();
        long td_st_str = today_start.getNano();
        System.out.println(timestamp);
    }

    private static void test() {
        System.out.println("LocalTime.MAX: " + LocalTime.MAX);
        System.out.println("LocalTime.MIN: " + LocalTime.MIN);
        System.out.println("LocalTime.MIDNIGHT: " + LocalTime.MIDNIGHT);

        // 默认获取UTC时区
        Instant instant=Instant.now();
        System.out.println("当前时间（UTC时区）:"+instant);
        System.out.println("获取时间戳:"+instant.toEpochMilli());

        // 偏移量运算
        OffsetDateTime offsetDateTime =instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println("当前时间:"+offsetDateTime);

        // 以Unix元年为起点，进行偏移量运算
        Instant instant2 = Instant.ofEpochSecond(60);
        System.out.println(instant2);


        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now().plusDays(1);
        Duration duration=Duration.between(from, to);
        // 7天
        Duration duration1 = Duration.of(7, ChronoUnit.DAYS);
        // 60秒
        Duration duration2 = Duration.of(60, ChronoUnit.SECONDS);

        durationTest("duration", duration);
        durationTest("duration1", duration1);
        durationTest("duration2", duration2);

        periodTest();

        //获取当前时区
        ZoneId z=ZoneId.systemDefault();

        System.out.println(z);

        LocalDateTime dateTime=LocalDateTime.now();
        String str=dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println(str);
        str = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(str);
    }

    private static void durationTest(String prefix, Duration duration) {
        //总天数
        long days=duration.toDays();
        //小时
        long hours=duration.toHours();
        //分钟
        long millis=duration.toMillis();
        //秒数
        long seconds=duration.getSeconds();
        //毫秒数
        long minutes=duration.toMinutes();
        //纳秒数
        long nanos=duration.toNanos();
        System.out.println();
        System.out.println(prefix + "_天数"+days);
        System.out.println(prefix + "_小时"+hours);
        System.out.println(prefix + "_分钟数"+minutes);
        System.out.println(prefix + "_秒数"+seconds);
        System.out.println(prefix + "_毫秒"+millis);
        System.out.println(prefix + "_纳秒数"+nanos);

    }

    private static void periodTest() {
        Period period=Period.of(2020, 1, 11);
        Period period1 = Period.between(LocalDate.now(), LocalDate.now().plusYears(1));

        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
        System.out.println(period.getUnits());
        System.out.println(period.getChronology());
        System.out.println(period.withDays(0).getYears());


    }

    private static void base64Test() {
        String str = "我的？啊啊runoob?java8";
        try {
            // 使用基本编码
            String base64encodedString = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
            System.out.println("Base64 编码字符串 (基本) :" + base64encodedString);
            // 解码
            byte[] bytes = Base64.getDecoder().decode(base64encodedString);
            System.out.println("原始字符串: " + new String(bytes, "utf-8"));

            // 使用URL编码
            String base64urlencodedString = Base64.getUrlEncoder().encodeToString(str.getBytes("utf-8"));
            System.out.println("Base64 编码字符串 (url) :" + base64urlencodedString);
            // 解码
            byte[] bytes2 = Base64.getUrlDecoder().decode(base64urlencodedString);
            System.out.println("原始字符串: " + new String(bytes2, "utf-8"));

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 10; ++i) {
                stringBuilder.append(UUID.randomUUID().toString());
            }
            byte[] bytes3=stringBuilder.toString().getBytes("utf-8");
            //使用MIME编码
            String base64MimeEncodedString =Base64.getMimeEncoder().encodeToString(bytes3);
            System.out.println("Base64 编码字符串 (MIME) :" + base64MimeEncodedString);
            byte[] bytes4 = Base64.getMimeDecoder().decode(base64MimeEncodedString);
            System.out.println("原始字符串: " + new String(bytes4, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
