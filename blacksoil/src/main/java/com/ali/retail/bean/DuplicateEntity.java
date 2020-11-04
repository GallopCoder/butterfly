package com.ali.retail.bean;

public class DuplicateEntity {
    private long id;
    private String value;
    private boolean exist;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DuplicateEntity{value: " + this.value + ", id: " + this.id + ", exist: " + this.exist + "}";
    }
}
