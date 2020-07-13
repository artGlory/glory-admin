package com.spring.common.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import com.warrenstrange.googleauth.KeyRepresentation;

import java.util.concurrent.TimeUnit;

public class GoogleAuthUtils {

    /**
     * 生成新的认证
     *
     * @return
     */
    public static GoogleAuthenticatorKey createCredentials() {
        GoogleAuthenticatorConfigBuilder gacb = new GoogleAuthenticatorConfigBuilder()
                .setKeyRepresentation(KeyRepresentation.BASE32);
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator(gacb.build());
        GoogleAuthenticatorKey credentials = googleAuthenticator.createCredentials();
        return credentials;
    }

    /**
     * 获取认证里面的key;key存入Google 身份验证器里面，获取动态密码
     *
     * @param credentials
     * @return
     */
    public static String getKey(GoogleAuthenticatorKey credentials) {
        return credentials.getKey();
    }

    /**
     * 根据认证，获取谷歌二维码地址；Google 身份验证器扫描二维码，获取动态密码
     *
     * @param platformName
     * @param username
     * @param credentials
     * @return
     */
    public static String getOtpAuthURL(String platformName, String username, GoogleAuthenticatorKey credentials) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(platformName, username, credentials);
    }

    /**
     * 验证方法
     *
     * @param googleKey
     * @param inputKey 用户动态密码
     * @return
     */
    public static boolean verify(String googleKey, int inputKey) {
        GoogleAuthenticatorConfigBuilder gacb = new GoogleAuthenticatorConfigBuilder()
                .setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(30)).setWindowSize(5).setCodeDigits(6);
        GoogleAuthenticator ga = new GoogleAuthenticator(gacb.build());
        return ga.authorize(googleKey, inputKey);
    }

    public static void main(String[] args) {
        GoogleAuthenticatorKey credentials = GoogleAuthUtils.createCredentials();
        String googleKey = credentials.getKey();//用户需要保存的key

        String authUrl = GoogleAuthUtils.getOtpAuthURL("platformName", "username", credentials);
        System.out.println(googleKey);
        System.out.println(authUrl);
    }
}