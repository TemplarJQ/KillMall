package com.seckillmall.service.impl;


import com.alibaba.druid.util.StringUtils;
import com.seckillmall.dao.UserDOMapper;
import com.seckillmall.dao.UserPasswordMapper;
import com.seckillmall.dataobject.UserDO;
import com.seckillmall.dataobject.UserPassword;
import com.seckillmall.error.BusinessException;
import com.seckillmall.error.EmBusinessError;
import com.seckillmall.service.UserService;
import com.seckillmall.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息为空");
        }

        if(org.apache.commons.lang3.StringUtils.isEmpty(userModel.getName())
            || userModel.getGender() == null
            || userModel.getAge() == null
            || org.apache.commons.lang3.StringUtils.isEmpty(userModel.getTelphone())){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //实现model转化为dataobject
        UserDO userDO = convertFromUserModel(userModel);
        userDOMapper.insertSelective(userDO);

        UserPassword userPassword = convertPasswordFromUserModel(userModel);
        userPasswordMapper.insertSelective(userPassword);

    }

    private UserPassword convertPasswordFromUserModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPassword userPassword = new UserPassword();
        userPassword.setEncrptPassword(userModel.getEncrptPassword());
        userPassword.setId(userModel.getId());
        return userPassword;
    }

    private UserDO convertFromUserModel(UserModel userModel){
        UserDO userDO = new UserDO();
        if(userModel == null){
            return null;
        }
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
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
