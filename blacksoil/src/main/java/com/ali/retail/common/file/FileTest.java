package com.ali.retail.common.file;

import java.io.File;

public class FileTest {


    public static void main(String[] args) {
        File file = new File("aa.txt");
        String name = file.getName();
        System.out.println(name.substring(name.lastIndexOf(".") + 1));
    }
}
