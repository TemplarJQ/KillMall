package com.seckillmall.dao;

import com.seckillmall.dataobject.ItemStockDO;

public interface ItemStockDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    ItemStockDO selectByItemId(Integer itemid);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey( ItemStockDO record);
}