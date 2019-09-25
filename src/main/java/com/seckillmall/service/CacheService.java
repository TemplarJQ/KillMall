package com.seckillmall.service;

public interface CacheService {

    //存方法
    void serCommonCache(String key, Object value);

    //取方法
    Object getFromCommonCache(String key);
}
