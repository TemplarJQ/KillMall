package com.seckillmall.error;


//包装器实现业务异常类
public class BusinessException extends Exception implements CommonError {

    //强关联一个Error
    private CommonError commonError;

    //直接接受EmBusinessError的传参用于构造业务异常
    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    //接受自定义ErrorMsg的方式接受业务异常
    public BusinessException(CommonError commonError, String errMsg) {
        super();
        this.commonError = commonError;
        this.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        return this.commonError.setErrMsg(errMsg);
    }
}
