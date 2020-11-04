package com.ali.retail.bean.fruit;

public class Fruit {
    protected String name;
    protected String shape;

    public Fruit() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", shape='" + shape + '\'' +
                '}';
    }
}
