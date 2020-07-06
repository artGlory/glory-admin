package com.spring.webapi.tools;

import com.spring.common.utils.JwtTokenTool;
import com.spring.common.utils.Moment;
import com.spring.webapi.constant.CommonConstants;

public class TokenTool {

    /**
     * @param iss       jwt签发者
     * @param sub       面向对象
     * @param aud       Audience 接收方
     * @param iat       签发时间
     * @param exp       结束时间
     * @param key       负载中的key
     * @param value     负载中key所对应的value
     * @param secretKey 签名密钥
     * @return
     */
    private static final String iss;
    private static final String sub;
    private static final String aud;
    //    private static final String iat;
//    private static final String exp;
    private static final int validHours;//token过期时间
    private static final String key;
    //    private static final String value;
    private static final String secretKey;

    static {
        iss = "web-api";
        sub = "api-user";
        aud = "api-web";
        validHours = 24 * 3;
        key = "api-uk";
        secretKey = CommonConstants.api_password_salt;
    }


    public static String createToken(String apiUserUk) {
        Moment nowMoment = new Moment();
        Moment expMoment = nowMoment.clone().add(validHours, "hours");
        String token = JwtTokenTool.createToken(
                iss, sub, aud, nowMoment.toDate(), expMoment.toDate(), key, String.valueOf(apiUserUk), secretKey
        );
        return token;
    }

    public static String getApiUserUk(String token) {
        String result = null;
        try {
            long expTime = JwtTokenTool.getExpirationTime(token, secretKey);
            if (new Moment().toDate().getTime() > expTime) {
                throw new IllegalArgumentException("登陆凭证已过期，请重新登陆");
            }
            String uk = JwtTokenTool.getPayloadValue(token, secretKey, key);
            result = uk;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return result;
    }


}
