package com.ali.retail.common.face;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * 有这样一道智力题：“某商店规定：三个空汽水瓶可以换一瓶汽水。
 * 小张手上有十个空汽水瓶，她最多可以换多少瓶汽水喝？”答案是5瓶，
 * 方法如下：先用9个空瓶子换3瓶汽水，喝掉3瓶满的，喝完以后4个空瓶子，用3个再换一瓶，喝掉这瓶满的，这时候剩2个空瓶子。
 * 然后你让老板先借给你一瓶汽水，喝掉这瓶满的，喝完以后用3个空瓶子换一瓶满的还给老板。
 * 如果小张手上有n个空汽水瓶，最多可以换多少瓶汽水喝？
 */

public class DrinkTest {

    public static int drink(int empty) {
        return drink(0, empty);
    }

    public static int drink(int drink, int empty){
        if(empty > 2) {
            int currentDrink = empty/3;
            drink += currentDrink;
            empty = currentDrink + empty%3;
            if(empty >= 2){
                drink = drink(drink, empty);
            }
        } else if (empty == 2) {
            drink ++;
        } else {
        }
        return drink;
    }

    public static int simpleDrink(int empty) {
        return empty/2;
    }



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int num = sc.nextInt();
            System.out.println(drink(num));
        }
        sc.close();
    }

}
