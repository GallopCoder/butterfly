package com.ali.retail.bean;

public class Single {

    //volatile 防止指令重排
    private volatile static Single instance;

    private Single() {}

    public static Single getInstanceByDoubleCheck() {
        if (instance == null) {
            synchronized (Single.class) {
                if (instance == null) {
                    instance = new Single();
                }
            }
        }
        return instance;
    }

    public static Single getInstanceByStaticInnerClass(){
        return Inner.instance;
    }

    private static class Inner {
        private static final Single instance = new Single();
    }
}
