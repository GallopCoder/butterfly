package com.ali.retail.bean.fruit;

public class Banana extends Fruit{

    private boolean face;

    @Override
    public String toString() {
        return "Banana{" +
                "face=" + face +
                ", name='" + name + '\'' +
                ", shape='" + shape + '\'' +
                '}';
    }
}
