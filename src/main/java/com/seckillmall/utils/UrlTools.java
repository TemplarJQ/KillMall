package com.seckillmall.utils;

import org.apache.commons.lang3.StringUtils;

public class UrlTools {

    //直接为模板url
    private static String url1 = "https://m.ifeng.com/miArticle?aid=ucms_7mUTXQGG1mp&version=2&mibusinessId={mibusinessId}&env={env}";
    //字段不正确
    private static String url2 = "https://m.ifeng.com/miArticle?aid=ucms_7mUTXQGG1mp&version=2&mibusinessId=miuibrowser&env=production";
    //测试
    private static String url3 = "https://m.ifeng.com/miArticle?aid=ucms_7mUTXQGG1mp&version=2";

    public static void main(String[] args) {
        func3(url2, "mibusinessId", "xiangkan");
    }

    public static void func3(String url, String param, String val) {
        url = url.replaceAll("(" + param +"=[^&]*)", param + "=" + val);
        System.out.println(url);
    }

    public static void func2(String url) {
        String temp = url1.replaceAll("\\{mibusinessId}", "xiangkan");
        System.out.println(temp.replaceAll("\\{env}", "production"));
    }


    public static String func(String url) {
        if (null == url) {
            return "";
        }
        url = url.trim();
        if(StringUtils.isBlank(url)){
            return "";
        }
        //先判断是否有指定字段
        if(StringUtils.contains(url,"mibusinessId") || StringUtils.contains(url,"env")){
            String[] urlParts = url.split("\\?");
            String address = urlParts[0];
            String params = urlParts[1];
            return url+"1";
        } else {
            return url;
        }
    }


}
