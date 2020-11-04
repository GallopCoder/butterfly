package com.ali.retail.common.base;

import java.util.Optional;

public class OptionalTest {

    public static void main(String[] args) {

        TestVo vo=new TestVo();
        vo.setName("我是测试");
        Optional<TestVo> opt= Optional.ofNullable(vo);
        String name=opt.map(TestVo::getName).get();
        System.out.println("Name是："+name);

        Optional<TestVo> opt2=Optional.ofNullable(null);
        String name2=opt2.map(v-> v.getName()).orElse("我是null");
        System.out.println("Name2是："+name2);

        Integer value1 = null;
        Integer value2 = new Integer(10);
        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> optional1= Optional.ofNullable(value1);
        //Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> optional2= Optional.of(value2);
        System.out.println(sum(optional1,optional2));
    }

    /**
     * 两个数相加
     * @return
     */
    public static Integer sum(Optional<Integer> a,Optional<Integer> b) {
        // Optional.isPresent - 判断值是否存在
        System.out.println("第一个参数值存在: " + a.isPresent());
        System.out.println("第二个参数值存在: " + b.isPresent());
        Integer value1=a.orElse(0);
        Integer value2=b.orElse(0);
        return value1+value2;
    }
}

class TestVo{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
