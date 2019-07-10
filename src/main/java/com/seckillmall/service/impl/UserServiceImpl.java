package com.seckillmall.service.impl;


import com.seckillmall.dao.UserDOMapper;
import com.seckillmall.dao.UserPasswordMapper;
import com.seckillmall.dataobject.UserDO;
import com.seckillmall.dataobject.UserPassword;
import com.seckillmall.service.UserService;
import com.seckillmall.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Override
    public UserModel getUserById(Integer id) {

        //查询对应的业务逻辑
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        //用户不存在
        if(userDO == null){
            return null;
        }
        //查询密码等加密信息
        UserPassword userPassword = userPasswordMapper.selectByUserId(id);

        return convertFromDataObject(userDO, userPassword);
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPassword userPassword){
        UserModel userModel = new UserModel();
        if(userDO == null){
            return null;
        }
        BeanUtils.copyProperties(userDO,userModel);
        if(userPassword != null){
            userModel.setEncrptPassword(userPassword.getEncrptPassword());
        }
        return userModel;
    }
}
