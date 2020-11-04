package com.ali.retail.common.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockUtil {

    private static final Lock NO_FAIR_Lock = new ReentrantLock();
    private static final Lock FAIR_Lock = new ReentrantLock(true);
    private static final Condition A = FAIR_Lock.newCondition();
    private static final Condition B = FAIR_Lock.newCondition();
    private static final Condition C = FAIR_Lock.newCondition();

    static ThreadLocal<String> s = new ThreadLocal<>();

    public static Condition getNewCondition(Lock lock) {
        return lock.newCondition();
    }


    public void lock() {
        FAIR_Lock.lock();
        FAIR_Lock.unlock();
    }
    
    public static Lock getRLock(boolean fair) {
        return new ReentrantLock(fair);
    }

    public static void lockA() {
        try {
            FAIR_Lock.lock();
            System.out.println("A has lock!");
            B.signal();
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Single B!");
            System.out.println("A will await!");
            A.await();//阻塞
            System.out.println("After single A!");
        } catch (Exception e) {

        } finally {
            System.out.println("A unlock");
            FAIR_Lock.unlock();
        }
    }

    public static void lockB() {
        try {
            FAIR_Lock.lock();
            System.out.println("B has lock!");
            C.signal();
            System.out.println("Single C!");
            System.out.println("B will await!");
            B.await();
            System.out.println("After single B");
        } catch (InterruptedException e) {

        } finally {
            System.out.println("B unlock");
            FAIR_Lock.unlock();
        }
    }

    public static void lockC() {
        try {
            FAIR_Lock.lock();
            System.out.println("C has lock!");
            A.signal();
            System.out.println("Single A!");
            System.out.println("C will await!");
            C.await();
            System.out.println("After single C");
        } catch (InterruptedException e) {

        } finally {
            System.out.println("C unlock");
            FAIR_Lock.unlock();
        }
    }


    public static void main(String[] args) {
//        s.set("sss");
//        s.set("aaa");
//        String a = s.get();
//        System.out.println(a);
//        try {
//            Condition condition = NO_FAIR_Lock.newCondition();
//            NO_FAIR_Lock.tryLock();
//            condition.await();
//
//            condition.await(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        new Thread(ReentrantLockUtil::lockA).start();
        new Thread(ReentrantLockUtil::lockB).start();
        new Thread(ReentrantLockUtil::lockC).start();
        try{
            Thread.sleep(1000);
            lockA();

        } catch (Exception e) {

        }
    }
}
