package com.ali.retail.common.generic;

import com.ali.retail.bean.Article;
import com.ali.retail.bean.DuplicateEntity;
import com.ali.retail.bean.fruit.Apple;
import com.ali.retail.bean.fruit.Fruit;

public class Generic<T> {

    void show_1(T t) {
        System.out.println("show_1  " + t.toString());
    }

    <E> void show_2(E e) {
        System.out.println("show_2  " + e.toString());
    }

    <T> void show_3(T t) {
        System.out.println("show_3  " + t.toString());
    }

    public static void main(String[] args) {
        Generic<Fruit> o = new Generic<Fruit>();
        Fruit f = new Fruit();
        Apple a = new Apple();
        Article article = new Article();
        System.out.println("show_1 演示________________________");
        o.show_1(f);
        o.show_1(a);
//        o.show_1( p );  是不能编译通过的。因为在 ClassName<Fruit>中已经限定了全局的T为Fruit，所以不能再加入Person;
        System.out.println("show_2 演示________________________");
        o.show_2(f);
        o.show_2(a);
        o.show_2(article);
        System.out.println("show_3 演示________________________");
        o.show_3(f);
        o.show_3(a);
        o.show_3(new DuplicateEntity());
    }
}
