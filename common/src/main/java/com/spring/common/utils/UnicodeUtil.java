package com.spring.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * unicode码转换
 *
 * @author shuai.ding
 * @date 2017年5月31日下午5:41:15
 */
public class UnicodeUtil {

    /**
     * 将字符串转化成unicode码
     *
     * @param string
     * @return
     * @author shuai.ding
     */
    public static String string2Unicode(String string) {

        if (StringUtils.isBlank(string)) {
            return null;
        }

        char[] bytes = string.toCharArray();
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            char c = bytes[i];

            // 标准ASCII范围内的字符，直接输出
            if (c >= 0 && c <= 127) {
                unicode.append(c);
                continue;
            }
            String hexString = Integer.toHexString(bytes[i]);

            unicode.append("\\u");

            // 不够四位进行补0操作
            if (hexString.length() < 4) {
                unicode.append("0000".substring(hexString.length(), 4));
            }
            unicode.append(hexString);
        }
        return unicode.toString();
    }


    /**
     * 将unicode码转化成字符串
     *
     * @param unicode
     * @return
     * @author shuai.ding
     */
    public static String unicode2String(String unicode) {
        if (StringUtils.isBlank(unicode)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = unicode.indexOf("\\u", pos)) != -1) {
            sb.append(unicode.substring(pos, i));
            if (i + 5 < unicode.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
            }
        }
        //如果pos位置后，有非中文字符，直接添加
        sb.append(unicode.substring(pos));

        return sb.toString();
    }

    public static void main(String[] args) {
        String str = "{\"status\":1,\"info\":\"\\u65b0\\u589e\\u6ce8\\u5355\\u6210\\u529f\",\"data\":false}";
        System.out.println(unicode2String(str));
        System.out.println(unicode2String(string2Unicode(str)));
    }
}
