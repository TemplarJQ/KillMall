package com.seckillmall.service;

import com.seckillmall.error.BusinessException;
import com.seckillmall.service.model.UserModel;

public interface UserService {

    //获取用户id
    UserModel getUserById(Integer id);

    UserModel getUserByIdInCache(Integer id);

    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telphone, String password) throws BusinessException;
}
