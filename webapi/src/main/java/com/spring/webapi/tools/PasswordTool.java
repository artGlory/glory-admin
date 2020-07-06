package com.spring.webapi.tools;

import com.spring.common.utils.MD5Tool;

/**
 * 密码处理工具
 */
public class PasswordTool {
    /**
     * 加密管理员密码
     *
     * @param password
     * @return
     */
    public static String encrypteApiUserPassword(String password) {
        return MD5Tool.md5TO32(password).toLowerCase();
    }

    /**
     * 验证管理员密码是否正确
     *
     * @param password
     * @param tPassword
     * @return
     */
    public static boolean validateApiUserPassword(String password, String tPassword) {
        return tPassword.equals(MD5Tool.md5TO32(password).toLowerCase());
    }
}
