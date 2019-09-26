package com.seckillmall.test;

import com.google.common.collect.Lists;
import com.seckillmall.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class testBiz {

    private MyBiz myBiz;

    public static void main(String[] args) {
        func9("测试");
    }

    public static String func9(String str) {
        System.out.println("这是函数9");
        return "hello" + str;
    }

    public static String func8(String str) {
        String[] list = str.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for(String temp: list){
            stringBuilder.append("\""+temp+"\",");
        }
        return stringBuilder.toString();
    }

    public static String func3(String str) {
        String[] list = str.split("=");
        List<String> strings = new ArrayList<>();
        for(String tt: list){
            String[] temp = tt.split(",");
            for(String cc: temp){
                strings.add("\""+cc+"\"");
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        int end  = strings.size();
        for(int i=0; i<end; i++){
            stringBuilder.append(strings.get(i));
            if(i%2 == 0){
                stringBuilder.append(":");
            }else {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    public static void func7() {
        List<String> list = Lists.newArrayList("a", "d" ,"c", "e");
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, Collections.reverseOrder());
        System.out.println(Arrays.toString(names.toArray()));
    }

    public static void func6() {
        LocalDateTime time1 = DateTimeUtils.convert(Long.parseLong("1556433222156"));
        LocalDateTime time2 = DateTimeUtils.convert(Long.parseLong("1552879377555"));
        LocalDateTime time3 = DateTimeUtils.convert(Long.parseLong("1556433222155"));
        System.out.println("绑定微信时间:  "+time1);
        System.out.println("用户创建时间:  "+time2);
        System.out.println("上次解绑时间:  "+time3);
    }


    public static void fun5() {
        myfor: for(int i=0; i<100;i++){
            for(int j=0;j<50;j++){
                if(i == 70){
                    System.out.println("find 70!");
                    break myfor;
                }
            }
        }
    }

    public static String func4(String str) {
        String[] list = str.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for(String temp: list) {
            stringBuilder.append("\""+temp+"\""+",");
        }
        return stringBuilder.toString();
    }

    public void func2() {
        String a = "true";
        String b = "false";
        Boolean aa = Boolean.parseBoolean(a);
        Boolean bb = Boolean.parseBoolean(b);
        System.out.println(aa+"-"+bb);
        Boolean cc = Boolean.getBoolean(a);
        boolean dd = Boolean.valueOf(a);
        System.out.println("cc"+cc+"-"+"dd"+dd);
        System.out.println(cc.getClass().toString());
    }

    public void func1() {
        List<User> list = new ArrayList<>();
        list.add(new User(12,"张三",1333));
        list.add(new User(13,"李四",1222));
        list.add(new User(14,"王五",1111));
        list.add(new User(15,"赵六",1111));
        testBiz test = new testBiz(new MyBiz(1,"恭喜发财",list));

        List<String> userName = test.myBiz.getPacketList().stream().map(e -> {
            if(1111 == e.getTel()){
                return e.getName();
            }
            return "";
        }).filter(e -> !"".equals(e)).collect(Collectors.toList());

        for(String s:userName){
            System.out.println(s);
        }

        System.out.println(userName.size());

        String str = UUID.randomUUID().toString();
        System.out.println(str);
    }


}
