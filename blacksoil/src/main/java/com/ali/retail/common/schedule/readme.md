##Executors构造线程池及其原理解析
###java.util.concurrent.Executors
    注：1.活跃线程数量小于池子大小时，若有新任务需要执行，线程池都会创建新的线程保证新任务的如期执行
    	2.若活跃线程在执行期间，因故障而关闭时，若有新任务需要执行，线程池都会创建新的线程保证新任务的如期执行
    	  (创建新线程的前提：活跃线程数量小于池子大小)
    	3.若活跃线程数已经达到池子的最大值，若有新任务需要执行，该任务需要等待，直到有可用线程，再执行
    
    a.创建固定数量的线程池	  
    public static ExecutorService newFixedThreadPool(int nThreads) {
            return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }
    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
            return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
        }
    b.创建可缓存的线程池	
    public static ExecutorService newCachedThreadPool() {
            return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        }
    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
            return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
        }
        
    c.创建单个线程的线程池	
    public static ExecutorService newSingleThreadExecutor() {
            return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
        }
    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
            return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory));
        }
        
    d.创建单个用于执行定时任务的线程池	
    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
            return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1));
        }
    public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
            return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1, threadFactory));
        }
    e.创建多个用于执行定时任务的线程池	
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
            return new ScheduledThreadPoolExecutor(corePoolSize);
        }
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
            return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
        }
    
    f.创建一个线程池，维护足够多的线程以支持任务队列并行执行(可以使用多个队列来减少争用)	
    public static ExecutorService newWorkStealingPool(int parallelism) {
            return new ForkJoinPool (parallelism, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
        }
    public static ExecutorService newWorkStealingPool() {
            return new ForkJoinPool (Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
        }
        
                                                        ||
                                                        \/
                                                        
    ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory)
    	
    ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);  
    
    DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(corePoolSize, threadFactory));    
    
    ForkJoinPool (parallelism, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);                                              
                                                        
                                                        ||
                                                        \/
                                                        
    ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory)
    	
    DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(corePoolSize, threadFactory));
    	注：public ScheduledThreadPoolExecutor(int corePoolSize) {
                  super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,new DelayedWorkQueue());//super:ThreadPoolExecutor
              }
    ForkJoinPool(parallelism, factory, handler, asyncMode)

                                                        ||
                                                        \/
    重点分析：（参考 https://www.cnblogs.com/WangHaiMing/p/8798709.html)
    1.ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory)    
      a.LinkedBlockingQueue
      b.SynchronousQueue
      c.DelayedWorkQueue
    2.ForkJoinPool(parallelism, factory, handler, asyncMode)         
    
    a.LinkedBlockingQueue
    
        public E poll(long timeout, TimeUnit unit) throws InterruptedException {
                E x = null;
                int c = -1;
                long nanos = unit.toNanos(timeout);
                final AtomicInteger count = this.count;
                final ReentrantLock takeLock = this.takeLock;
                takeLock.lockInterruptibly();
                try {
                    while (count.get() == 0) {//count代表queue的当前容量
                        if (nanos <= 0)
                            return null;
                        nanos = notEmpty.awaitNanos(nanos);
                    }
                    x = dequeue();//返回队首节点
                    c = count.getAndDecrement();
                    if (c > 1)
                        notEmpty.signal();
                } finally {
                    takeLock.unlock();
                }
                if (c == capacity)
                    signalNotFull();
                return x;
            }
            
            public final long awaitNanos(long nanosTimeout)
                            throws InterruptedException {
                        if (Thread.interrupted())
                            throw new InterruptedException();
                        Node node = addConditionWaiter();//添加一个新节点到队列尾，并将节点返回
                        long savedState = fullyRelease(node);//释放节点。释放成功，保持状态CONDITION;否则，置为CANCELLED
                        final long deadline = System.nanoTime() + nanosTimeout;
                        int interruptMode = 0;
                        while (!isOnSyncQueue(node)) {//校验当前添加的node是否在同步队列中(不在同步队列中，则进入循环；否则跳过)
                            if (nanosTimeout <= 0L) {
                                transferAfterCancelledWait(node);
                                break;
                            }
                            if (nanosTimeout >= spinForTimeoutThreshold)
                                LockSupport.parkNanos(this, nanosTimeout);//调用底层(native方法)挂起
                            if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                                break;
                            nanosTimeout = deadline - System.nanoTime();
                        }
                        if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                            interruptMode = REINTERRUPT;
                        if (node.nextWaiter != null)
                            unlinkCancelledWaiters();
                        if (interruptMode != 0)
                            reportInterruptAfterWait(interruptMode);
                        return deadline - System.nanoTime();
                    }
    
        private Node addConditionWaiter() {//添加一个新节点到同步队列，并将节点返回
                    Node t = lastWaiter;
                    // If lastWaiter is cancelled, clean out.
                    if (t != null && t.waitStatus != Node.CONDITION) {
                        unlinkCancelledWaiters();
                        t = lastWaiter;
                    }
                    Node node = new Node(Thread.currentThread(), Node.CONDITION);
                    if (t == null)
                        firstWaiter = node;
                    else
                        t.nextWaiter = node;
                    lastWaiter = node;
                    return node;
                }
        
        final long fullyRelease(Node node) {//释放节点。释放成功，保持状态CONDITION;否则，置为CANCELLED
                boolean failed = true;
                try {
                    long savedState = getState();
                    if (release(savedState)) {
                        failed = false;
                        return savedState;
                    } else {
                        throw new IllegalMonitorStateException();
                    }
                } finally {
                    if (failed)
                        node.waitStatus = Node.CANCELLED;
                }
            }
        
        final boolean isOnSyncQueue(Node node) {
                /** waitStatus value to indicate thread is waiting on condition */
                //static final int CONDITION = -2;
                if (node.waitStatus == Node.CONDITION || node.prev == null)//状态为CONDITION，标识处于等待处理状态(已不在同步队列中)
                    return false;
                if (node.next != null) // If has successor, it must be on queue
                    return true;
                /*
                 * node.prev can be non-null, but not yet on queue because
                 * the CAS to place it on queue can fail. So we have to
                 * traverse from tail to make sure it actually made it.  It
                 * will always be near the tail in calls to this method, and
                 * unless the CAS failed (which is unlikely), it will be
                 * there, so we hardly ever traverse much.
                 */
                return findNodeFromTail(node);//从队列尾向对首开始找该节点，找到返回true，否则返回false
            }
        
        private E dequeue() {
                // assert takeLock.isHeldByCurrentThread();
                // assert head.item == null;
                Node<E> h = head;
                Node<E> first = h.next;
                h.next = h; // help GC
                head = first;
                E x = first.item;
                first.item = null;
                return x;
            }                                       