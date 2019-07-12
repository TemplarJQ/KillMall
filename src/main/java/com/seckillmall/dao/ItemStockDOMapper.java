package com.seckillmall.dao;

import com.seckillmall.dataobject.ItemStockDO;
import org.apache.ibatis.annotations.Param;

public interface ItemStockDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    ItemStockDO selectByItemId(Integer itemid);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey( ItemStockDO record);
}