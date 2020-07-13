package com.spring.common.aop;

import com.spring.common.cache.CacheUtil;
import com.spring.common.domain.condition.help.BaseCondition;
import com.spring.common.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * cacheDao
 */
@Slf4j
@Aspect
@Component
public class CacheDaoAspect {
    @Autowired
    private CacheUtil cacheUtil;

    @Pointcut("execution(* com.spring.common.mybatis..*Mapper.*(..))")
    public void Pointcut() {
    }

    @Around("Pointcut()")
    public Object Around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Map<String, Object> map = new HashMap<>();
        //获取目标类名称
        String clazzName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
        //获取目标类方法名称
        String methodName = proceedingJoinPoint.getSignature().getName();
        //返回类型
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Class returnType = method.getReturnType();
        //参数
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        for (Object obj : args) {
            sb.append(obj.getClass().getSimpleName());
            sb.append("[");
            sb.append(obj);
            sb.append("]");
            if (obj instanceof BaseCondition) {//重新计算分页
                ((BaseCondition) obj).reCalcIndex();
            }
        }
        String params = sb.toString();

        /*
        cacheDao
         */
        boolean falg = false;
        Object result = null;
        if (methodName.contains("search")
                || methodName.contains("list")
                || methodName.contains("count")
                || methodName.contains("select")
        ) {
            if (returnType.toString().contains("java.util.List")) {
                result = cacheUtil.getList(clazzName, methodName + params, null);
            } else {
                result = cacheUtil.get(clazzName, methodName + params, null);
            }
            if (result == null) {
                falg = true;
                result = proceedingJoinPoint.proceed();
                cacheUtil.setCache(clazzName, methodName + params, result);
            }
        } else if (methodName.contains("insert")
                || methodName.contains("update")
                || methodName.contains("delete")
        ) {
            result = proceedingJoinPoint.proceed();
            cacheUtil.cleanSpace(clazzName);
        } else {
            log.error("请重新定义方法名称。*mapper里面的方法必须包含（search|list|count|select|insert|update|delete）。" +
                    clazzName + "." + methodName);
        }

        if (returnType.toString().equals("long")) {//redis-long会自动转化为int,直接转换为long会异常
            result = ObjectUtils.toLong(result);
        }
        map.put("clazzName", clazzName);
        map.put("methodName", methodName);
        map.put("returnType", returnType);
        map.put("params", params);
        if (falg)
            log.error("非缓存" + map.toString());
        else
            log.error(map.toString());
        return result;
    }

}
