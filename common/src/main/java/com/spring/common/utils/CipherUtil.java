package com.spring.common.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * 加密解密工具
 */
public class CipherUtil {


    private static BouncyCastleProvider bouncyCastleProvider = null;

    /**
     * 获取Bouncy Castle Provider；
     * 获取加密方法算法的提供商；
     * 使用单例模式防止静态数据导致的内存宕机
     *
     * @return
     */
    private static synchronized BouncyCastleProvider getInstance() {
        if (bouncyCastleProvider == null) {
            bouncyCastleProvider = new BouncyCastleProvider();
            Security.addProvider(bouncyCastleProvider);//动态添加provider
        }
        return bouncyCastleProvider;
    }

    /**
     * 计算信息摘要，获取MD5
     *
     * @param str
     * @return 长度为32位
     */
    public static String getMD5(String str) {
        java.security.MessageDigest messageDigest = null;
        try {
            messageDigest = java.security.MessageDigest.getInstance("MD5", getInstance());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(str.getBytes());
        byte[] digesta = messageDigest.digest();
        return bytesToHex(digesta);
    }

    /**
     * byte[] 转换 为十六进制字符串
     * byte[] bytes = {0x0F, 0x1F, 0x2F, 0x3F, 0x4F, 0x5F, 0x6F};
     *
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }


}
