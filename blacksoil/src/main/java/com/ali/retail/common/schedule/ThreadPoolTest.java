package com.ali.retail.common.schedule;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class ThreadPoolTest {


    public static void test() {
        ExecutorService scheduledPool = CustomThreadPool.getScheduledPool(1, null);
        scheduledPool.submit(new TimerTask() {
            @Override
            public void run() {

            }
        });
    }

    public static void main(String[] args) {
        test();
    }
}
