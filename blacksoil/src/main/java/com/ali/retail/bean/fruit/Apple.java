package com.ali.retail.bean.fruit;

public class Apple extends Fruit{

    private int age;

    @Override
    public String toString() {
        return "Apple{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", shape='" + shape + '\'' +
                '}';
    }
}
