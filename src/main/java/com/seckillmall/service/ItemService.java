package com.seckillmall.service;

import com.seckillmall.error.BusinessException;
import com.seckillmall.service.model.ItemModel;

import java.util.HashMap;
import java.util.List;

public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItem();

    //商品详情浏览
    ItemModel getItemById(Integer id);

    //验证item及promo model缓存模型
    ItemModel getItemByIdInCache(Integer id);

    //减少库存
    Boolean decreaseStock(Integer itemId, Integer amount);

    //增加销量
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;

}
