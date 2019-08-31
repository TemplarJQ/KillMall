package com.seckillmall.controller;

import com.seckillmall.aop.BaseCheckConfig;
import com.seckillmall.response.CommonReturnType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("test")
@RequestMapping("/test")
public class TestController {

    @GetMapping("/mytest")
    @BaseCheckConfig
    @ResponseBody
    public CommonReturnType doSomeTest(@RequestParam(value = "id")String str) {
        return CommonReturnType.create(str, "测试aop");
    }
}
