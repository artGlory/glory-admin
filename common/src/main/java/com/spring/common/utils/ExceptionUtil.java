package com.spring.common.utils;

public class ExceptionUtil {
    /**
     * 获取异常的所有信息；message+stackTrace
     *
     * @param t
     * @return message+stackTrace
     */
    public static String getThrowableAllMessage(Throwable t) {
        StringBuffer stringBuffer = new StringBuffer(t.getMessage() + "\r\n");
        for (StackTraceElement stackTraceElement : t.getStackTrace()) {
            stringBuffer.append(stackTraceElement.toString());
            stringBuffer.append("\r\n");
        }
        return stringBuffer.toString();
    }
}
