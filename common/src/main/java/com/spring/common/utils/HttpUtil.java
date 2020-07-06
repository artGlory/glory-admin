package com.spring.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    /**
     * 获取远程请求ip地址
     *
     * @param request
     * @return
     */
    public static String getRemoteAddr(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                String ipAddress = inetAddress.getHostAddress();
                ip = ipAddress;
            } catch (Exception e) {

            }
        }
        if (ip.split(",").length > 0) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    /**
     * 格式化session
     *
     * @return
     * @requestVO request
     */
    public static String formatSessionId(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sid = session.getId();
        // example: 012345678901234567890123456789AB!0123456789A
        // example: 012345678901234567890123456789AB!0123456789B
        if (sid.indexOf("!") != -1) {
            sid = sid.substring(0, sid.indexOf("!"));
        }
        // example: 012345678901234567890123456789AB.tomcat1
        // example: 012345678901234567890123456789AB.tomcat2
        if (sid.indexOf(".") != -1) {
            sid = sid.substring(0, sid.indexOf("."));
        }
        return sid;
    }

    /**
     * 获取请求头
     *
     * @param httpServletRequest
     * @return
     */
    public static Map<String, Object> getHeaders(final HttpServletRequest httpServletRequest) {
        Map<String, Object> headMap = new HashMap<>();
        Enumeration<String> headers = httpServletRequest.getHeaderNames();
        while (headers.hasMoreElements()) {
            try {
                String key = headers.nextElement();
                Object value = httpServletRequest.getHeader(key);
                if (!StringUtils.isEmpty(value)) {
                    headMap.put(key, value);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return headMap;
    }

    /**
     * 获取请求参数
     *
     * @param httpServletRequest
     * @return
     */
    public static Map<String, Object> getParameters(final HttpServletRequest httpServletRequest) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            try {
                String key = parameterNames.nextElement();
                Object value = httpServletRequest.getParameterValues(key);
                if (!StringUtils.isEmpty(value)) {
                    paramMap.put(key, value);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return paramMap;
    }

    /**
     * 获取request域
     *
     * @param httpServletRequest
     * @return
     */
    public static Map<String, Object> getAttributes(final HttpServletRequest httpServletRequest) {
        Map<String, Object> attrMap = new HashMap<>();
        Enumeration<String> attributeNames = httpServletRequest.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            try {
                String key = attributeNames.nextElement();
                Object value = httpServletRequest.getAttribute(key);
                if (!StringUtils.isEmpty(value)) {
                    attrMap.put(key, value);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return attrMap;
    }

    /**
     * 获取request body
     *
     * @param httpServletRequest
     * @return
     */
    public static JsonNode getRequestBody(HttpServletRequest httpServletRequest) {
        JsonNode jsonNode = null;

        try {
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = httpServletRequest.getReader();
                while ((line = bufferedReader.readLine()) != null)
                    stringBuffer.append(line);
            } catch (Throwable t) {

            } finally {
                try {
                    if (bufferedReader != null) bufferedReader.close();
                } catch (Exception e) {

                }
            }
            jsonNode = new ObjectMapper().readTree(stringBuffer.toString());
        } catch (Throwable t) {

        }
        return jsonNode;
    }
}
