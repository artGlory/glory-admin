package com.spring.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token工具
 */
public class JwtTokenTool {

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
    public static String createToken(String iss, String sub, String aud, Date iat, Date exp
            , String key, String value, String secretKey) {
        /*
         * 参数验证
         */
        if (iat.getTime() > exp.getTime()) {
            throw new IllegalArgumentException("【iat<=exp】【iat:" + iat + ";exp:" + exp + "】");
        }
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        String token = JWT.create().withHeader(map) // header
                .withIssuer(iss)    //jwt签发者
                .withSubject(sub)   //面向对象
                .withAudience(aud)    //接收方
                .withIssuedAt(iat) // 签发时间
                .withExpiresAt(exp) // 结束时间
                .withClaim(key, value)
                .sign(Algorithm.HMAC256(secretKey)); // signature  签名密钥
        return token;
    }

    /**
     * @param token     jwt TOKEN
     * @param secretKey 签名密钥
     * @return
     */
    public static DecodedJWT verifyToken(String token, String secretKey) {
        DecodedJWT jwtDecode = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            jwtDecode = verifier.verify(token);
        } catch (Exception e) {

        }
        return jwtDecode;
    }

    /**
     * 获取payloadKey对应的值
     *
     * @param token
     * @param secretKey
     * @param payloadKey
     * @return
     */
    public static String getPayloadValue(String token, String secretKey, String payloadKey) {
        String payloadValue = null;
        try {
            payloadValue = verifyToken(token, secretKey).getClaim(payloadKey).asString();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return payloadValue;
    }

    /**
     * 获取过期时间戳-已转换位13位
     *
     * @param token
     * @param secretKey
     * @return
     */
    public static long getExpirationTime(String token, String secretKey) {
        long expTime = verifyToken(token, secretKey).getExpiresAt().getTime();
        return expTime;
    }

    public static String getPayloadJson(String token) {
        byte[] bytes = Base64.getUrlDecoder().decode(token.split("\\.")[1]);
        return new String(bytes);
    }


    public static void main(String[] args) {
        //加密
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + 1000L * 60);//60秒后过期
        String payloadKey = "payloadKey";
        String payloadValue = "payloadValue";
        String secretKey = "123445";
        String token = createToken("planServer", "user", "ios", iat, exp, payloadKey, payloadValue, secretKey);
        System.out.println(token);
        System.out.println(getPayloadJson(token));
        //验证
        verifyToken(token, secretKey);
        //获取payloadValue
        String payloadValueResult = getPayloadValue(token, secretKey, payloadKey);
        System.out.println(payloadValueResult);
        System.out.println(getExpirationTime(token, secretKey));
    }
}
