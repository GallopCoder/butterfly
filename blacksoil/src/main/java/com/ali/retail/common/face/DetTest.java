package com.ali.retail.common.face;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DetTest {
    private static Queue queue = new LinkedList();
    public static void main(String[] args) {
        queue.poll();
        queue.offer(queue.poll());
        dTest(8);
    }

    public static void dTest(int n) {
        if (n < 0) {
            n = 0;
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        int i = 0;
        while (list.size() > 1) {
            i = (i + 2) % list.size();
            list.remove(i);
        }

        System.out.println(list.get(0));
    }
}
