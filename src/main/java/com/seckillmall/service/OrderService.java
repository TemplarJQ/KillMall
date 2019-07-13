package com.seckillmall.service;

import com.seckillmall.error.BusinessException;
import com.seckillmall.service.model.OrderModel;

public interface OrderService {

    //1.通过前端url传入活动id，完成对于id下是否有活动？活动时间？活动是否属于商品？的校验
    //2.直接在下单接口内判断对应商品是否存在秒杀活动，若存在进行中的则以秒杀价格下单
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
