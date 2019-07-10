package com.seckillmall.controller;

import com.seckillmall.controller.viewobject.UserVO;
import com.seckillmall.error.BusinessException;
import com.seckillmall.error.EmBusinessError;
import com.seckillmall.response.CommonReturnType;
import com.seckillmall.service.impl.UserServiceImpl;
import com.seckillmall.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        //调用service服务并获取对象
        UserModel userModel = userService.getUserById(id);

        //获取对应用户信息不存在
        if(userModel == null){
            throw new BusinessException(EmBusinessError.User_NOT_EXIST);
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

    //定义ExceptionHandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)//屏蔽tomcat自己的处理
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {
        BusinessException exception = (BusinessException) ex;
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus("fail");
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("errCode", exception.getErrCode());
        responseData.put("errMsg", exception.getErrMsg());
        commonReturnType.setData(responseData);
        return commonReturnType;
    }




}
