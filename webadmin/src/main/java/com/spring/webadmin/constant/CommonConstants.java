package com.spring.webadmin.constant;

import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CommonConstants {
    /**
     * 用于request,response  设置唯一标识
     */
    public static final String http_uuid = "requestUuid";
    /**
     * 用于request设置时间
     */
    public static final String http_time = "requestTime";
    /**
     * 用户过滤器判断response是否异常
     */
    public static final String success_response = "successResponse";
    /**
     * admin密码加密salt
     */
    public static final String admin_password_salt = "2e3e6e9c-16d3-4d17-9063-2125c17e9f80";
    /**
     * header里面token存储位置
     */
    public static final String admin_token_key = "web-admin-token";
    /**
     * 错误编码
     */
    public static List<Integer> http_error_code_list = Arrays.asList(405, 404, 403, 500, 501);
    /**
     * 放行的uri
     */
    public static final Set<String> IGNORE_URL = ImmutableSet.of("/swagger", "/webjars");
    /**
     * 顶级uk
     */
    public static final String top_uk = "111222333444aaabbbcccddd99988877";
}
