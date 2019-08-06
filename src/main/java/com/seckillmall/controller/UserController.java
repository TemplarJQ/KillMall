package com.seckillmall.controller;

import com.alibaba.druid.util.StringUtils;
import com.seckillmall.controller.viewobject.UserVO;
import com.seckillmall.error.BusinessException;
import com.seckillmall.error.EmBusinessError;
import com.seckillmall.response.CommonReturnType;
import com.seckillmall.service.impl.UserServiceImpl;
import com.seckillmall.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
//对跨域请求参数进行设置保证session中的信息跨域得到读取
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone")String telphone,
                                  @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if(com.alibaba.druid.util.StringUtils.isEmpty(telphone) ||
                StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //进行校验
        UserModel userModel = userService.validateLogin(telphone, this.enCodeByMD5(password));
        //将登陆凭证加入到用户登陆成功的session中
        UserVO userVo = convertFromModel(userModel);
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        System.out.println(this.httpServletRequest.getSession().getAttribute("IS_LOGIN"));

        return CommonReturnType.create(null);
    }

    @Transactional
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name= "telphone")String telphone,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")Integer gender,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "password")String password ,
                                     @RequestParam(name = "otpCode")String otpCode
                                     ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //首先校验optcode
        String inSessionCode = (String)this.httpServletRequest.getSession().getAttribute(telphone);
        if(!com.alibaba.druid.util.StringUtils.equals(inSessionCode, otpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "注册短信验证错误");
        }

        //然后操作service进行用户注册
        UserModel userModel = new UserModel();
        userModel.setTelphone(telphone);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setName(name);
        userModel.setRegisterMode("byphone");
        userModel.setThirdPartyId("");
        userModel.setEncrptPassword(this.enCodeByMD5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);

    }

    public String enCodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Base64.Encoder encoder = Base64.getEncoder();
        String newStr = encoder.encodeToString(md5.digest(str.getBytes("utf-8")));
//        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 加密字符串
//        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }


    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
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
