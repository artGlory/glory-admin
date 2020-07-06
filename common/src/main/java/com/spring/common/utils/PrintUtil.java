package com.spring.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PrintUtil {
    /**
     * 返回异常栈
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 返回异常信息
     * @param t
     * @return
     */
    public static String getMessge(Throwable t) {
        return t.getMessage();
    }
}
