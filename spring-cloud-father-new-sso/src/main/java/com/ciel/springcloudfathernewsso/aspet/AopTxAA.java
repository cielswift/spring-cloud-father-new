package com.ciel.springcloudfathernewsso.aspet;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopTxAA {

    //  对所有带有AutoLog注解的方法记录日志。
    @Pointcut("@annotation(com.ciel.api.anno.AopTe)")
    public void point() {
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint pjp) throws Exception {

        System.out.println("AOP执行");
        Object rtValue = null;
        try {
            Object[] args = pjp.getArgs();//得到方法执行所需的参数
            rtValue = pjp.proceed(args);//明确调用业务层方法（切入点方法）
            System.out.println("AOP执行结束");
            return rtValue;
        } catch (Throwable t) {
            System.out.println("AOP执行异常");
            throw new Exception("AAA");
        } finally {

        }
    }

}
