package com.spring.common.utils;

import java.nio.charset.Charset;

/**
 * 字节数组与16进制字符串转换工具
 */
public class HexTool {
    /**
     * 字节数组=》16进制字符串
     *
     * @param input
     * @return
     */
    public static String toHex(byte input[]) {
        if (input == null)
            return null;
        StringBuffer output = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            int current = input[i] & 0xff;
            if (current < 16)
                output.append("0");
            output.append(Integer.toString(current, 16));
        }
        return output.toString();
    }

    /**
     * 16进制字符串=》字节数组
     *
     * @param input
     * @return
     */
    public static byte[] fromHex(String input) {
        if (input == null)
            return null;
        byte output[] = new byte[input.length() / 2];
        for (int i = 0; i < output.length; i++)
            output[i] = (byte) Integer.parseInt(
                    input.substring(i * 2, (i + 1) * 2), 16);
        return output;
    }

    public static void main(String[] args) {
//        byte[] bytes = new byte[]{Integer.valueOf(1).byteValue()};
        byte[] bytes = "123456".getBytes(Charset.forName("UTF-8"));
        System.out.println(toHex(bytes));
        System.out.println(new String(fromHex(toHex(bytes)), Charset.forName("UTF-8")));
    }

}
