package com.ali.retail.common.aspect;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BlackSoilAspect {

    private Logger logger = LoggerFactory.getLogger(BlackSoilAspect.class);
    @Pointcut("execution(public * com.ali.retail.contorller.*.*(..))")
    public void addAdvice(){}

    // 将对应的入参带上方法签名一一打印
    @Before("addAdvice()")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String mn = signature.getDeclaringTypeName() + "." + signature.getName();
        if(args != null && args.length >0) {
            for(Object obj : args) {
                if(obj != null)
                    logger.info("[" + mn + " - param] " + JSON.toJSONString(obj));
            }
        }
    }

    //对有返回结果的接口，打印最终相应返回结果
    @AfterReturning(returning = "result", pointcut = "addAdvice()")
    public void after(JoinPoint joinPoint, Object result){
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        if(result != null)
            logger.info("[" + methodName + " - result] " + JSON.toJSONString(result));
    }

}
