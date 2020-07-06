package com.spring.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5解密验证
 * MD5 加密后的位数一般为两种，16 位与 32 位。16 位实际上是从 32 位字符串中，取中间的第 9 位到第 24 位的部分
 */
public class MD5Tool {
    /**
     * MD5 32位
     *
     * @param text 明文
     * @return 密文 32位
     * @throws Exception
     */
    public static String md5TO32(String text) {
        //加密后的字符串
        String encodeStr = DigestUtils.md5Hex(text);
        return encodeStr;
    }

    /**
     * MD5  16位
     *
     * @param text
     * @return
     */
    public static String md5TO16(String text) {
        return md5TO32(text).substring(8, 24);
    }


    public static void main(String[] args) {
        System.out.println(md5TO32("123456").toLowerCase());
    }
}