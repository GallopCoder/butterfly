package com.ali.retail.common.queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class LinkTest {

    public void linkTest() {
        List<String> list = new LinkedList<>();
    }

    public void SynchronousQueue() throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        queue.put("");
        queue.drainTo(new ArrayList<>());
    }

    public static void test(int a) {
        boolean x = a!=3&&a!=4&&a!=5&&a!=6;
        System.out.println(x);
        if (a!=3&&a!=4&&a!=5&&a!=6) {
            System.out.println("if true");
        } else {
            System.out.println("if false");
        }
        byte b = 127;
        b += b;
    }

    public static void main(String[] args) {
        test(4);
    }
}
