package com.ali.retail.utils.concurrent.bs_lock;


import java.util.concurrent.TimeUnit;


public interface BS_Lock {

    void lock();


    void lockInterruptibly() throws InterruptedException;


    boolean tryLock();


    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();


    BS_Condition newCondition();
}
