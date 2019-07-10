package com.seckillmall.controller;

import com.seckillmall.controller.viewobject.UserVO;
import com.seckillmall.service.impl.UserServiceImpl;
import com.seckillmall.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/get")
    @ResponseBody
    public UserVO getUser(@RequestParam(name="id")Integer id){
        //调用service服务并获取对象
        UserModel userModel = userService.getUserById(id);
        //将核心领域模型用户转UI给用户使用的模型
        return convertFromModel(userModel);
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
