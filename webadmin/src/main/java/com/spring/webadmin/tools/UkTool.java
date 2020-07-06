package com.spring.webadmin.tools;

import java.util.UUID;

public class UkTool {
    /**
     * 数据库主键
     *
     * @return
     */
    public static String getUk32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        int i = 0;
        while (i <= 5) {
            System.out.println(getUk32());
            i++;
        }
    }
}
