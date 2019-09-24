package com.seckillmall.service.model;

import java.io.Serializable;
import java.math.BigDecimal;

//解决用户下单的交易模型
public class OrderModel implements Serializable{

    //企业级别应用的交易号是要记录时间的明显格式，如20190701+88888
    private String id;

    //购买的用户id
    private Integer userId;

    //商品id
    private Integer itemId;

    //若非空，则表示是以秒杀商品方式下单
    private Integer promoId;

    //购买商品的单价，这家伙是到时候写秒杀用的东西，变化的时候要用，区分于商品价格，若promoId非空则表示秒杀商品价格
    private BigDecimal itemPrice;

    //数目
    private Integer amount;

    //总金额，若promoId非空则表示秒杀商品价格
    private BigDecimal orderAmount;

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}
