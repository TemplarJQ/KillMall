package com.seckillmall.controller;

import com.alibaba.druid.util.StringUtils;
import com.seckillmall.controller.viewobject.UserVO;
import com.seckillmall.error.BusinessException;
import com.seckillmall.error.EmBusinessError;
import com.seckillmall.response.CommonReturnType;
import com.seckillmall.service.impl.UserServiceImpl;
import com.seckillmall.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/register")
    public CommonReturnType register(@RequestParam(name="telphone")String telphone,
                                     @RequestParam(name = "id")Integer id,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")Byte gender,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "optcode")String optCode
                                     ) throws BusinessException {
        //首先校验optcode
        String inSessionCode = (String)this.httpServletRequest.getSession().getAttribute("telphone");
        if(!com.alibaba.druid.util.StringUtils.equals(inSessionCode, optCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "注册短信验证错误");
        }

        //然后操作service进行用户注册
    }


    @RequestMapping("/getotp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone")String telphone){
        //获取otp随机码
        Random random = new Random();
        int randomCode = random.nextInt(99999);
        String optCode = String.valueOf(randomCode + 10000);

        //与Httpsession进行绑定
        httpServletRequest.getSession().setAttribute(telphone, optCode);

        //将optcode返回给用户
        System.out.println("telphone = "+ telphone + " & optdoe = "+ optCode);

        return CommonReturnType.create(null);

    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        //调用service服务并获取对象
        UserModel userModel = userService.getUserById(id);

        //获取对应用户信息不存在
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        //将核心领域模型用户转UI给用户使用的模型
        UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel){
        //转化为视图
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

}
