package com.ali.retail.common.stream;

import com.zhongsou.common.VConvert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class FIStream {

    public static String getByteArrayMd5(byte[] bb) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte aa[] = md.digest(bb);
        StringBuffer sb = new StringBuffer(32);
        String s = null;
        for (int i = 0; i < aa.length; i++) {
            s = Integer.toHexString((int) aa[i] & 0xff);
            if (s.length() < 2) sb.append('0');
            sb.append(s);
        }
        String m = sb.toString();
        //System.out.println(m);
        return m;
    }


    public static void main(String[] args) throws Exception {
        InputStream in = new FileInputStream("D:\\start-history-server.sh");
        byte bb[]=new byte[8];
        in.read(bb, 0,8);
        System.out.println(bb);
        long tv= VConvert.bytes2Long(bb);
        System.out.println(tv);
        in.read(bb,0,4);
        System.out.println(getByteArrayMd5(bb));
        int len=VConvert.bytes2Int(bb);
        System.out.println(new StringBuffer(len));
    }
}
