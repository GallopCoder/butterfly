package com.ali.retail.common.forkjoin;

import com.ali.retail.prism.PrismUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DetectData {

    private String data;
    private int index;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private Set<DetectData> detect(List<DetectData> dataList) {
        Set<DetectData> set = new HashSet<>();
        //        PrismUtil.getWordMatchSubIds("ps", "ps", data);
        // todo 将打完标签的数据按索引回置，并放回set。
        return set;
    }

    private DetectData detect(DetectData data) {

        return null;
    }
}
