package com.seckillmall.controller;

import com.seckillmall.error.BusinessException;
import com.seckillmall.error.EmBusinessError;
import com.seckillmall.response.CommonReturnType;
import com.seckillmall.service.OrderService;
import com.seckillmall.service.model.OrderModel;
import com.seckillmall.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller("order")
@RequestMapping("/order")
//对跨域请求参数进行设置保证session中的信息跨域得到读取
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    OrderService orderService;

    @Autowired
    HttpServletRequest httpServletRequest;

    //封装下单
    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "promoId", required = false) Integer promoId,
                                  @RequestParam(name = "amount") Integer amount) throws BusinessException {


        //获取用户id
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "用户未登录");
        }
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId, amount);

        return CommonReturnType.create(null);

    }

}
