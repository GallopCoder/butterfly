package com.ali.retail.common.jvm;

public class StackTraceTest {

    public static void test() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();//调用的类名
        String methodName = stackTrace[2].getMethodName();//调用的方法名
        System.out.println(className + " " + methodName);
    }

    public static void test1() {
        test();
    }

    public static void test2(){
        ClassLoader cl = StackTraceTest.class.getClassLoader();
        cl.equals("");
    }
    public static void main(String[] args) {
        test1();
    }
}
