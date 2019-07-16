package com.seckillmall.controller;

import com.seckillmall.error.BusinessException;
import com.seckillmall.error.EmBusinessError;
import com.seckillmall.response.CommonReturnType;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonReturnType doError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Exception ex) {

        ex.printStackTrace();
        Map<String,Object> responseData = new HashMap<>();
        if( ex instanceof BusinessException){

            BusinessException businessException = (BusinessException)ex;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());

        }else if(ex instanceof ServletRequestBindingException){

            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg","url绑定路由问题");

        }else if(ex instanceof NoHandlerFoundException){

            responseData.put("errCode",EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg","对应的访问路径无法找到");

        }else{

            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrMsg());

        }
        return CommonReturnType.create(responseData,"fail");
    }
}
