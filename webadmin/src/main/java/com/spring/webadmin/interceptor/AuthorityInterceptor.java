package com.spring.webadmin.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.common.po.AdminPrivilege;
import com.spring.common.utils.Moment;
import com.spring.webadmin.anno.NoNeedLogin;
import com.spring.webadmin.anno.NoValidPrivilege;
import com.spring.webadmin.constant.CommonConstants;
import com.spring.webadmin.domain.vo.ResponseDate;
import com.spring.webadmin.module.adminPrivilege.tools.PrivilegeTool;
import com.spring.webadmin.module.adminUser.domain.AdminInfoDTO;
import com.spring.webadmin.module.adminUser.tools.AdminTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 用户权限验证；
 * 验证顺序：放行的url=>NoNeedLogin=>登陆验证=>NoValidPrivilege=>权限验证
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isHandlerMethod = handler instanceof HandlerMethod;
        if (!isHandlerMethod) {
            return true;
        }

        //放行的Uri前缀
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String target = uri.replaceFirst(contextPath, "");
        for (String ignoryUri : CommonConstants.IGNORE_URL) {
            if (target.startsWith(ignoryUri)) {
                return true;
            }
        }

        //不需要登录的注解
        Boolean isNoNeedLogin = ((HandlerMethod) handler).getMethodAnnotation(NoNeedLogin.class) != null;
        if (isNoNeedLogin) {
            return true;
        }

        //用户登陆验证
        AdminInfoDTO adminInfoDTO = null;
        try {
            adminInfoDTO = AdminTool.getAdminUser(request);
        } catch (Throwable t) {
            responsePrint(response, t.getMessage());
            return false;
        }
        if (adminInfoDTO == null) {
            responsePrint(response, "请重新登陆");
            return false;
        }

        //判断接口权限
        String methodName = ((HandlerMethod) handler).getMethod().getName();
        String className = ((HandlerMethod) handler).getBeanType().getName();
        String[] nameArr = className.split("\\.");
        String controllerName = nameArr[nameArr.length - 1];
        Method m = ((HandlerMethod) handler).getMethod();
        Class<?> cls = ((HandlerMethod) handler).getBeanType();
        boolean isClzAnnotation = cls.isAnnotationPresent(NoValidPrivilege.class);
        boolean isMethodAnnotation = m.isAnnotationPresent(NoValidPrivilege.class);
        NoValidPrivilege noValidPrivilege = null;
        if (isClzAnnotation) {
            noValidPrivilege = cls.getAnnotation(NoValidPrivilege.class);
        } else if (isMethodAnnotation) {
            noValidPrivilege = m.getAnnotation(NoValidPrivilege.class);
        }
        if (noValidPrivilege != null) {
            return true;
        } else {
            String privilegePath = controllerName + "." + methodName;
            AdminPrivilege adminPrivilege = PrivilegeTool.getByPrivilegePath(privilegePath);
            if (adminPrivilege == null) {
                responsePrint(response, "权限不足");
                log.error("[没用此权限]" + adminInfoDTO.getName() + ";" + privilegePath);
                return false;
            } else {
                boolean hasPermission = false;
                for (String permission : adminInfoDTO.getPermissions()) {
                    if (permission.equals(String.valueOf(adminPrivilege.getUk())))
                        hasPermission = true;
                }
                if (hasPermission) {
                    return true;
                } else {
                    responsePrint(response, "权限不足");
                    log.error("[没用此权限]" + adminInfoDTO.getName() + ";" + privilegePath);
                    return false;
                }
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        response.setHeader(CommonConstants.http_time, new Moment().toSimpleTime());
    }

    private void responsePrint(final HttpServletResponse httpServletResponse, String errMessage) throws IOException {
        httpServletResponse.setStatus(200);
        //跨域请求
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //设置编码格式
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter pw = httpServletResponse.getWriter();
        pw.write(new ObjectMapper().writeValueAsString(ResponseDate.builder().success(false).message(errMessage).build()));
        pw.flush();
        pw.close();
    }
}