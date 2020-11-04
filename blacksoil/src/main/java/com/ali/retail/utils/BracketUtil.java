package com.ali.retail.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


/**
 * 成对符号相关计算工具类
 */
public class BracketUtil {

    /**
     * 计算字符串中括号所在索引，并封装对象，存放在集合中
     * @param var0 待计算的字符串
     */
    private static void compute(String var0) {
        for (int i = 0; i< BracketBucket.symbol_left.length; i++) {
            checkAndCompute(var0, i);
        }
    }

    /**
     * 校验和计算
     * @param var0 待计算字符串
     * @param index 括号标识数组的索引下标
     * @return 括号是否成对存在
     */
    private static boolean checkAndCompute(String var0, int index) {
        boolean flag = true;
        if(StringUtils.isNotBlank(var0)) {
            String leftSymbol = BracketBucket.symbol_left[index];
            String rightSymbol = BracketBucket.symbol_right[index];
            Stack<Bracket> pbrackets = BracketBucket.pmap.computeIfAbsent(leftSymbol, k -> new Stack<>());
            Stack<Bracket> brackets = BracketBucket.map.computeIfAbsent(leftSymbol, k -> new Stack<>());
            char[] chars = var0.toCharArray();
            int left = 0;
            int right = 0;
            for(int i=0;i<chars.length;i++) {
                String cs = chars[i] + "";
                if(cs.equalsIgnoreCase(leftSymbol)) {
                    left++;
                    pbrackets.add(new Bracket(i));
                    int deep = BracketBucket.deep[index];
                    BracketBucket.deep[index] = Math.max(pbrackets.size(), deep);
                } else if(cs.equalsIgnoreCase(rightSymbol)) {
                    right++;
                    Bracket bracket = pbrackets.pop();
                    if(bracket == null) {
                        pbrackets.clear();
                        brackets.clear();
                        BracketBucket.deep[index] = -1;
                        return false;
                    }
                    bracket.setRight(i);
                    brackets.add(bracket);
                }
            }
            flag = left == right;
        }
        return flag;
    }

    /**
     * 返回计算后的结果
     * @param var0 待计算的字符串
     * @return
     */
    public static Map<String, Stack<Bracket>> result(String var0) {
        compute(var0);
        return BracketBucket.map;
    }

    /**
     * 定义成对符号桶结构：存放所有“成对符号”对象
     */
    private static class BracketBucket {
        //已完成配对符号对象的存储容器
        private static Map<String, Stack<Bracket>> map = new HashMap<>(16);
        //未完成配对符号的存储容器
        private static Map<String, Stack<Bracket>> pmap = new HashMap<>(16);
        //初始化左括号标识 (可自定义)
        private static final String[] symbol_left = new String[]{"(", "[", "{"};
        //初始化右括号标识 (可自定义)
        private static final String[] symbol_right = new String[]{")", "]", "}"};
        //同类型符号的最大深度
        private static final int[] deep = new int[symbol_left.length];

        static {
            for (String str: symbol_left) {
                map.put(str, new Stack<>());
                pmap.put(str, new Stack<>());
            }
        }

        @Override
        public String toString() {
            return "BracketBucket{" +
                    "map=" + map +
                    '}';
        }
    }

    /**
     * 定义成对符号类
     */
    private static class Bracket{
        //左符号索引
        private int left;
        //右符号索引
        private int right;

        public Bracket(int left) {
            this.left = left;
        }

        public Bracket(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "Bracket{" +
                    "left=" + left +
                    ", right=" + right +
                    '}';
        }
    }


    public static void main(String[] args) {
        String var0 = "((广东|深圳|广州|珠海|汕头|佛山|韶关|湛江|肇庆|江门|茂名|惠州|梅州|汕尾|河源|阳江|清远|潮州|东莞|揭阳|中山|云浮)+(营业|企业亏损|翻工|破产|停工停产|基本工资|裁员|复工|复产|上班|停薪|待业|失业|网上办公|在家办公|返岗|停工)+(肺炎|疫情))+(-((免费|回复|预约|温馨提示|男女不限|假期|新闻播报|楼盘|接龙|兼职|股市|申请材料|航线)))";
        Map<String, Stack<Bracket>> result = BracketUtil.result(var0);
        System.out.println(Arrays.toString(BracketBucket.deep));
        System.out.println(result);
        System.out.println(var0.indexOf("(-"));
        System.out.println(var0.length() - 1);
    }

}

