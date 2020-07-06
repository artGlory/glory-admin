package com.spring.webapi.filter.logFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.common.utils.HttpUtil;
import com.spring.common.utils.Moment;
import com.spring.webapi.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 请求异常的时候，log日志；记录所有入参；配合GlobalExceptionHandler使用；
 * LogFilter记录入参， GlobalExceptionHandler记录异常；两者有相同uuid
 */
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.error("【Filter】(uuid) init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        LogWrappedRequest logWrappedRequest = new LogWrappedRequest(httpServletRequest);//getReader() has already been called for this request
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Map<String, Object> requestMap = null;
        if (logWrappedRequest.getAttribute(CommonConstants.http_uuid) == null) {
            requestMap = new HashMap<>();
            requestMap.put("headers", HttpUtil.getHeaders(logWrappedRequest));
            requestMap.put("parameters", HttpUtil.getParameters(logWrappedRequest));
//            requestMap.put("attributes", HttpUtil.getAttributes(httpServletRequest));
            requestMap.put("requestBody", HttpUtil.getRequestBody(logWrappedRequest));

            String time = new Moment().toFullTime();
            String uuid = UUID.randomUUID().toString();
            logWrappedRequest.setAttribute(CommonConstants.http_uuid, uuid);
            logWrappedRequest.setAttribute(CommonConstants.http_time, time);
            requestMap.put(CommonConstants.http_time, logWrappedRequest.getAttribute(CommonConstants.http_time));
            requestMap.put(CommonConstants.http_uuid, logWrappedRequest.getAttribute(CommonConstants.http_uuid));
            requestMap.put("RUI", logWrappedRequest.getRequestURI());
            requestMap.put("IP", HttpUtil.getRemoteAddr(logWrappedRequest));

            httpServletResponse.setHeader(CommonConstants.http_uuid, uuid);
        }

        chain.doFilter(logWrappedRequest, response);

        httpServletResponse.setHeader(CommonConstants.http_time, new Moment().toFullTime());
        if (httpServletResponse.getHeader(CommonConstants.success_response) != null
                && httpServletResponse.getHeader(CommonConstants.success_response).equals("false")) {
            log.error(requestMap.get("IP") + " " + new ObjectMapper().writeValueAsString(requestMap));
        }
    }

    @Override
    public void destroy() {
        log.error("【Filter】(uuid) destroy");
    }
}
