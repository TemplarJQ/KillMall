package com.seckillmall.service;

import com.seckillmall.error.BusinessException;
import com.seckillmall.service.model.UserModel;

public interface UserService {

    //获取用户id
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
}