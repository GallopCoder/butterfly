package com.ali.retail.common.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import com.ali.retail.bean.DataBeanParent;
import com.ali.retail.prism.PrismUtil;

public class DetectTask extends RecursiveTask<List<Set<String>>> {

    private  int threshold ;
    private static final int segmentation = 3;// 拆分最小长度
    private List<? extends DataBeanParent> data;
    private int fromIndex;
    private int toIndex;
    private List<Set<String>> target = new ArrayList<>();

    public DetectTask(List<? extends DataBeanParent> data, int fromIndex, int toIndex) {
        this.data = data;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.threshold = data.size()/segmentation;
    }

    @Override
    protected List<Set<String>> compute() {
        if((toIndex - fromIndex) < threshold ){// 已拆分至最小任务
            return deal(copyList(data, fromIndex, toIndex));
        }else{// 再拆分
            int mid = (fromIndex+toIndex)/2;
            DetectTask left =  new DetectTask(data, fromIndex, mid);
            DetectTask right = new DetectTask(data,mid+1, toIndex);
            invokeAll(left,right);
            left.fork();
            List<Set<String>> l = left.join();
            List<Set<String>> r = right.join();
            System.out.println("========left.join(): " + l);
            System.out.println("========right.join(): " + r);
            target.addAll(l);//这里处理，已经顺序打乱。需要对处理的数据进行索引绑定
            target.addAll(r);
            return target;
        }
    }

    public List<? extends DataBeanParent> copyList(List<? extends DataBeanParent> data, int fromIndex, int toIndex) {
        return new ArrayList<>(data.subList(fromIndex, toIndex));
    }


    private List<Set<String>> deal(List<? extends DataBeanParent> data) {
        return PrismUtil.getWordMatchSubIds("ps", "ps", data);
    }



    public static void forkJoinTest() {
        ForkJoinPool forkJoinPool= new ForkJoinPool();
        ArrayList<DataBeanParent> data = getDataBeanParents();
        DetectTask task = new DetectTask(data,0, data.size());
        long start = System.currentTimeMillis();
        forkJoinPool.invoke(task);
        System.out.println("The count is "+task.join()
                +" spend time:"+(System.currentTimeMillis()-start)+"ms");
    }

    public static void test() {
        ArrayList<DataBeanParent> data = getDataBeanParents();
        System.out.println("test==========" + PrismUtil.getWordMatchSubIds("ps", "ps", data));
    }

    private static ArrayList<DataBeanParent> getDataBeanParents() {
        ArrayList<DataBeanParent> data = new ArrayList<>();
        data.add(new DataBeanParent("上海交通大学", "P2P之在深圳逾期"));
        data.add(new DataBeanParent("上海智能交通大学", "地铁茂名"));
        data.add(new DataBeanParent("上海的李强", "国庆长假出游"));
        data.add(new DataBeanParent("黄浦江", "上海虹桥"));
        data.add(new DataBeanParent("上海地铁", "圣诞节"));
        data.add(new DataBeanParent("上港+恒大+足协杯+半决赛", "国庆长假出游"));
        return data;
    }

    public static void main(String[] args) {

        forkJoinTest();

    }
}
