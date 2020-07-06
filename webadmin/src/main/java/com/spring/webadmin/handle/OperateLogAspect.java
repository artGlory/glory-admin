package com.spring.webadmin.handle;

import com.spring.common.po.LogOperate;
import com.spring.common.utils.Moment;
import com.spring.common.utils.PrintUtil;
import com.spring.webadmin.job.LogOperateSaveJob;
import com.spring.webadmin.module.adminUser.domain.AdminInfoDTO;
import com.spring.webadmin.module.adminUser.tools.AdminTool;
import com.spring.webadmin.tools.UkTool;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 操作日志记录
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Pointcut("@annotation(com.spring.webadmin.anno.OperateLog)")
    public void logPointCut() {
    }

    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(request);
            // 设置方法名称
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            String operateMethod = className + "." + methodName;
            //参数
            Object[] args = joinPoint.getArgs();
            StringBuilder sb = new StringBuilder();
            for (Object obj : args) {
                sb.append(obj.getClass().getSimpleName());
                sb.append("[");
                sb.append(obj);
                sb.append("]");
            }
            String params = sb.toString();
            //swagger注解
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            String operateTyep = "为获取到操作类别";
            ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
            if (method != null && apiOperation != null) {
                operateTyep = apiOperation.value();
            }
            //请求结果
            int resultFlag = LogOperate.result_normal;
            if (e != null) {
                resultFlag = LogOperate.result_exception;
            }

            LogOperate logOperate = LogOperate.builder()
                    .uk(UkTool.getUk32())
                    .operator(adminInfoDTO == null ? "未获取到用户" : adminInfoDTO.getName())
                    .operatorUk(adminInfoDTO == null ? "未获取到用户" : adminInfoDTO.getAdminUk())
                    .operatorRoleUk(adminInfoDTO == null ? "未获取到用户" : adminInfoDTO.getAdminRole().getUk())
                    .operateName(operateTyep)
                    .operatePath(operateMethod)
                    .params(params)
                    .result(resultFlag)
                    .failReason(e == null ? "" : PrintUtil.getStackTrace(e))
                    .createTime(new Moment().toDate())
                    .updateTime(new Moment().toDate())
                    .build();
            LogOperateSaveJob.addOperateLog(logOperate);

        } catch (Exception exp) {
            log.error("保存操作日志异常:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

}