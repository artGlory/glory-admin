package com.spring.webadmin.tools;

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
    public static String encrypteAdminUserPassword(String password) {
        return MD5Tool.md5TO32(password).toLowerCase();
    }

    /**
     * 验证管理员密码是否正确
     *
     * @param password
     * @param encryptionPassword
     * @return
     */
    public static boolean validateAdminUserPassword(String password, String encryptionPassword) {
        return encryptionPassword.equals(MD5Tool.md5TO32(password).toLowerCase());
    }
}
