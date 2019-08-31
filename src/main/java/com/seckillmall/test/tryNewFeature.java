package com.seckillmall.test;

import java.util.Arrays;

public class tryNewFeature {

    public static void main(String[] args) {

        int[] a = {1,2,3,4,5};
        for(int x: a){
            System.out.println(x);
            x = x + 1;
        }
        System.out.println(Arrays.toString(a));

    }

    public static void fun3() {
        String str = "hi";
        StringBuilder stringBuilder = new StringBuilder("hello");
        changeSB(stringBuilder);
        changeStr(str);
        System.out.println(str);
        System.out.println(stringBuilder.toString());
    }

    public static void changeStr(String str) {
        str += "li hua";
    }

    public  static void changeSB(StringBuilder str) {
        str.append(", jiaqi");
        str = new StringBuilder("hey");
        str.append(", li hua");
    }

    public static void fun2() {
        //测试double是否准确
        System.out.println(3*0.1);

    }

    public static void fun1() {
        //这里测试一个包装类的问题，那就是包装类对于高频数据会有缓冲，比如int型就是-128~127
        Integer num1 = 127;
        Integer num2 = 127;
        System.out.println(num1 == num2);
        Integer num3 = 128;
        Integer num4 = 128;
        System.out.println(num3 == num4);
        //使用equals则是对值的比较
        System.out.println(num1.equals(num2));
        System.out.println(num3.equals(num4));
    }
}
