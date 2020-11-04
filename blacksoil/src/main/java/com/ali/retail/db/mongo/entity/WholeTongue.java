package com.ali.retail.db.mongo.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "whole_tongue")
public class WholeTongue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String name;
    private String address;
    private double price;
    private Date life;
    private int age;


    public WholeTongue() {
    }

    public WholeTongue(Long id, String name, String address, double price, Date life, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.life = life;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getLife() {
        return life;
    }

    public void setLife(Date life) {
        this.life = life;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "WholeTongue{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", life=" + life +
                ", age=" + age +
                '}';
    }
}
