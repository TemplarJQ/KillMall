package com.seckillmall.error;


public enum EmBusinessError implements CommonError {

    //通用错误类型10001，解决入参校验
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    //未知错误
    UNKNOWN_ERROR(10002, "未知错误"),

    //20000开头表示为用户信息相关定义错误
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002,"手机或密码不存在"),
    USER_NOT_LOGIN(20003,"用户还未登陆"),

    //30000开头表示交易信息错误定义
    STOCK_NOT_ENOUGH(30001, "库存不足")
    ;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
