package com.spring.common.help;

import java.security.Provider;
import java.security.Security;

public class Check {
    public static void main(String[] args) {
        System.out.println("-------列出加密服务提供者-----");
        Provider[] pro = Security.getProviders();
        System.out.println(pro.length);
        for (Provider p : pro) {
            System.out.println("Provider:" + p.getName() + " - version:" + p.getVersion());
            System.out.println(p.getInfo());
        }
        System.out.println("");
        System.out.println("-------列出系统支持的消息摘要算法：");
        for (String s : Security.getAlgorithms("MessageDigest")) {
            System.out.println(s);
        }
        System.out.println("-------列出系统支持的生成公钥和私钥对的算法：");
        for (String s : Security.getAlgorithms("KeyPairGenerator")) {
            System.out.println(s);
        }
    }
}  