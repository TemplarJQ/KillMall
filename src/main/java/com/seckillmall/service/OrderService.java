package com.seckillmall.service;

import com.seckillmall.error.BusinessException;
import com.seckillmall.service.model.OrderModel;

public interface OrderService {

    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
}
