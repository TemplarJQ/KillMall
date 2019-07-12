package com.seckillmall.service.impl;

import com.seckillmall.dao.ItemDOMapper;
import com.seckillmall.dao.ItemStockDOMapper;
import com.seckillmall.dao.UserDOMapper;
import com.seckillmall.error.BusinessException;
import com.seckillmall.error.EmBusinessError;
import com.seckillmall.service.ItemService;
import com.seckillmall.service.OrderService;
import com.seckillmall.service.UserService;
import com.seckillmall.service.model.ItemModel;
import com.seckillmall.service.model.OrderModel;
import com.seckillmall.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemStockDOMapper itemStockDOMapper;

    @Override
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {

        //校验信息
        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }
        if(amount <=0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品数量不合法，需要在0-99");
        }

        //落单减库存



        //创建交易信息


        //返回给前端

        return null;
    }


}
