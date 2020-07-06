package com.spring.webapi.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * controller超时记录
 */
@Slf4j
@Aspect
@Component
public class ControllerTimeOutAspect {

    @Pointcut("execution(* com.spring.webapi.module..*Controller.*(..))")
    public void Pointcut() {
    }

    @Around("Pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        Map<String, Object> map = new HashMap<>();
        //获取目标类名称
        String clazzName = pjp.getTarget().getClass().getName();
        //获取目标类方法名称
        String methodName = pjp.getSignature().getName();
        //记录类名称
        map.put("clazzName", clazzName);
        //记录对应方法名称
        map.put("methodName", methodName);
        // 计时并调用目标函数
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();
        long consumeTime = endTime - startTime;//耗时
        //设置消耗总时间
        map.put("consumeTime", consumeTime + "milliseconed");
        if (consumeTime > 1000L * 5) {
            log.error("【运行时间超时】" + new ObjectMapper().writeValueAsString(map));
        }
        return result;
    }

}
