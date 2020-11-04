package com.ali.retail.common.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class AddTest extends RecursiveTask<Integer> {

    private List<Integer> data = new ArrayList<>();
    private int fromIndex = 0;
    private int toIndex = data.size();
    private  int threshold;//任务量
    private static final int segmentation = 3;// 单个任务最小长度

    public AddTest(List<Integer> data, int fromIndex, int toIndex) {
        this.data = data;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.threshold = data.size()/segmentation;
    }

    @Override
    protected Integer compute() {
        if(toIndex - fromIndex <= threshold) {
            int total = (fromIndex+toIndex)/2;
            for (int i = fromIndex; fromIndex < toIndex; fromIndex++) {
                total += data.get(fromIndex);
            }
            return total;
        } else {
            int middle = 0;
            AddTest left = new AddTest(data, fromIndex, middle);
            AddTest right = new AddTest(data, middle, toIndex);
            invokeAll(left, right);
            return left.join() + right.join();
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        List<Integer> list = new ArrayList<>();

        AddTest task = new AddTest(list, 0, list.size()-1);
        pool.submit(task);
    }
}
