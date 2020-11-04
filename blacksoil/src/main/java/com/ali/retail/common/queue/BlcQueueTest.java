package com.ali.retail.common.queue;

import java.util.Collection;
import java.util.concurrent.*;

public class BlcQueueTest {

    BlockingQueue abQueue = new ArrayBlockingQueue(10);
    BlockingQueue lbQueue = new LinkedBlockingQueue();
    BlockingQueue pbQueue = new PriorityBlockingQueue();
    BlockingQueue dbQueue = new DelayQueue();
    BlockingQueue sbQueue = new SynchronousQueue();
    BlockingQueue ltQueue = new LinkedTransferQueue();
    BlockingQueue lbDeque = new LinkedBlockingDeque();

    public static BlockingQueue abQueue(int capacity, boolean fair, Collection c) {
        BlockingQueue queue;
        if (c == null || c.size() <= 0) {
            queue = new ArrayBlockingQueue(capacity, fair);
        } else {
            queue = new ArrayBlockingQueue(capacity, fair, c);
        }
        return queue;
    }

    public void put(BlockingQueue queue, Object object) throws InterruptedException {
        queue.put(object);
    }

    public void get(BlockingQueue queue) {
        queue.poll();
    }

}
