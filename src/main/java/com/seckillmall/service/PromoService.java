package com.seckillmall.service;

import com.seckillmall.service.model.PromoModel;

public interface PromoService {
    //即将进行或正在进行的秒杀活动
    PromoModel getPromoModelById(Integer itemId);
}
