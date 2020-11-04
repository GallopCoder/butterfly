package com.ali.retail.common.lock;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JLockTest {

    public static void jLockTest() throws InterruptedException {
        Object o = new Object();
        synchronized (JLockTest.class) {
            System.out.println("ss");
        }
        TimeUnit.SECONDS.sleep(2);
    }

    public synchronized void jLockTest2() {
        System.out.println("ss");
    }

    public static void timerTest() {
        Thread thread = new Thread();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

            }
        }, 1000, 2000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("-------设定要指定任务--------");
            }
        },10, 1000);
    }

    public static void lockTest() {
        Lock l = new ReentrantLock();
        l.lock();
        try {
            // access the resource protected by this lock
        } finally {
            l.unlock();
        }
    }
}
