package com.ali.retail.common.forkjoin;

import java.util.Set;

public class ForkData {
    private int index;
    private Set<String> tags;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

}
