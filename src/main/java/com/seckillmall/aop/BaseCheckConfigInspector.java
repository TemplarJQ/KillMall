package com.seckillmall.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class BaseCheckConfigInspector {

    @Pointcut("@annotation(com.seckillmall.aop.BaseCheckConfig)")
    private void pointcut(){
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("这是一个around");
        return pjp.proceed();
    }

    @Before(value = "pointcut()")
    public Object before(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("这是一个before");
        return pjp.proceed();
    }

}
