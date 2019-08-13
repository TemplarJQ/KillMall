package com.seckillmall.test;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class testBiz {

    private MyBiz myBiz;

    public static void main(String[] args) {
//        List<User> list = new ArrayList<>();
//        list.add(new User(12,"张三",1333));
//        list.add(new User(13,"李四",1222));
//        list.add(new User(14,"王五",1111));
//        list.add(new User(15,"赵六",1111));
//        testBiz test = new testBiz(new MyBiz(1,"恭喜发财",list));
//
//        List<String> userName = test.myBiz.getPacketList().stream().map(e -> {
//            if(1111 == e.getTel()){
//                return e.getName();
//            }
//            return "";
//        }).filter(e -> !"".equals(e)).collect(Collectors.toList());
//
//        for(String s:userName){
//            System.out.println(s);
//        }
//
//        System.out.println(userName.size());
//
//        String str = UUID.randomUUID().toString();
//        System.out.println(str);
        String a = "true";
        String b = "false";
        Boolean aa = Boolean.parseBoolean(a);
        Boolean bb = Boolean.parseBoolean(b);
        System.out.println(aa+"-"+bb);
        Boolean cc = Boolean.getBoolean(a);
        System.out.println(cc);

    }


}
