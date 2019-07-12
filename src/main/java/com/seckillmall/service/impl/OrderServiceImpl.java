package com.seckillmall.service.impl;

import com.seckillmall.dao.*;
import com.seckillmall.dataobject.OrderDO;
import com.seckillmall.dataobject.SequenceDO;
import com.seckillmall.error.BusinessException;
import com.seckillmall.error.EmBusinessError;
import com.seckillmall.service.ItemService;
import com.seckillmall.service.OrderService;
import com.seckillmall.service.UserService;
import com.seckillmall.service.model.ItemModel;
import com.seckillmall.service.model.OrderModel;
import com.seckillmall.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    SequenceDOMapper sequenceDOMapper;

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
        Boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //创建交易信息，订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setItemId(itemId);
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderAmount(itemModel.getPrice().multiply(new BigDecimal(amount)));

        orderModel.setId(getOrderId());

        OrderDO orderDO = this.convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //返回给前端
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    //这里为什么非要改成oublic ？？
    public String getOrderId(){
        /*
        订单号16位，前八位为时间信息，年月日（用于归档消除数据库使用），
        中间6位为自增序列（保证订单号不重复，如果超过6位数字还要再增大），
        最后2位为分库分表位（00到99，对订单进行用户水平拆分）
         */

        StringBuilder stringBuilder = new StringBuilder();
        //前8位
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.BASIC_ISO_DATE).replace("-","");//测试java8
        stringBuilder.append(nowDate);

        //中间为自增序列6位
        //通过新建表实现
        int sequnce = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequnce = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);

        String str = String.valueOf(sequnce);
        for(int i=0; i < 6-str.length(); i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(str);

        //分库分表位（先写死）00
        stringBuilder.append("00");

        return stringBuilder.toString();

    }

    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        return orderDO;
    }
}
