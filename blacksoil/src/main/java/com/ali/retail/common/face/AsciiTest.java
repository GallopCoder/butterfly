package com.ali.retail.common.face;

import java.util.Scanner;

public class AsciiTest {


    public static void main(String[] args) throws Exception{
        Scanner sc=new Scanner(System.in);
        while (sc.hasNext()){
            String str=sc.next();
            System.out.println(Integer.decode(str));//根据输入字符串的前缀，判断是何种进制数据
        }
    }
}
