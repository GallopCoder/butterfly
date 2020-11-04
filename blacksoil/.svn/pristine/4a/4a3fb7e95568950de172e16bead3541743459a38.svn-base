package com.ali.retail.utils.concurrent.bs_lock;

import java.util.concurrent.TimeUnit;
import java.util.Date;


public interface BS_Condition {

    void await() throws InterruptedException;


    void awaitUninterruptibly();

    long awaitNanos(long nanosTimeout) throws InterruptedException;

    boolean await(long time, TimeUnit unit) throws InterruptedException;

    boolean awaitUntil(Date deadline) throws InterruptedException;

    void signal();

    void signalAll();
}
