package com.ali.retail.common.schedule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class CustomThreadPool {

    public static ExecutorService getFixedPool(int poolSize, ThreadFactory threadFactory) {
        if (threadFactory != null) {
            return Executors.newFixedThreadPool(poolSize, threadFactory);
        }
        return Executors.newFixedThreadPool(poolSize);
    }

    public static ExecutorService getCachePool(ThreadFactory threadFactory) {
        if (threadFactory != null) {
            return Executors.newCachedThreadPool(threadFactory);
        }
        return Executors.newCachedThreadPool();
    }

    public static ExecutorService getScheduledPool(int poolSize, ThreadFactory threadFactory) {
        if (threadFactory != null) {
            return Executors.newScheduledThreadPool(poolSize, threadFactory);
        }
        return Executors.newScheduledThreadPool(poolSize);
    }



    public static void shutdown(ThreadPoolExecutor executor) {
        if (executor != null) {
            executor.shutdown();
        }
    }
}
