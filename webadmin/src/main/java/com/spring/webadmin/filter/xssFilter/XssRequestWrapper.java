package com.spring.webadmin.filter.xssFilter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.owasp.validator.html.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class XssRequestWrapper extends HttpServletRequestWrapper {

    private static Policy policy = null;

    static {
        String path = XssRequestWrapper.class.getClassLoader()
                .getResource("antisamy-myspace-1.4.4.xml").getFile();
        if (path.startsWith("file")) {
            path = path.substring(6);
        }
        try {
            policy = Policy.getInstance(path);
        } catch (PolicyException e) {
            e.printStackTrace();
        }
    }

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @SuppressWarnings("rawtypes")
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> request_map = super.getParameterMap();
        Iterator iterator = request_map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry me = (Map.Entry) iterator.next();
            String[] values = (String[]) me.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = xssClean(values[i]);
            }
        }
        return request_map;
    }

    public String[] getParameterValues(String paramString) {
        String[] arrayOfString1 = super.getParameterValues(paramString);
        if (arrayOfString1 == null)
            return null;
        int i = arrayOfString1.length;
        String[] arrayOfString2 = new String[i];
        for (int j = 0; j < i; j++)
            arrayOfString2[j] = xssClean(arrayOfString1[j]);
        return arrayOfString2;
    }

    public String getParameter(String paramString) {
        String str = super.getParameter(paramString);
        if (str == null)
            return null;
        return xssClean(str);
    }

    public String getHeader(String paramString) {
        String str = super.getHeader(paramString);
        if (str == null)
            return null;
        return xssClean(str);
    }

    private String xssClean(String value) {
        AntiSamy antiSamy = new AntiSamy();
        try {
            //antiSamy无法将编码后的字符进行过滤，所以统一进行decode
//            value = URLDecoder.decode(value, "utf-8");
            final CleanResults cr = antiSamy.scan(value, policy);
            String str = StringEscapeUtils.unescapeHtml4(cr.getCleanHTML());
            str = str.replaceAll(antiSamy.scan("&nbsp;", policy).getCleanHTML(), "");
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll(" ");
            return cleanSQLInject(str);
        } catch (ScanException e) {
            e.printStackTrace();
        } catch (PolicyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    //需要增加通配，过滤大小写组合
    public String cleanSQLInject(String src) {
        String temp = src;
        src = src.replaceAll(" insert ", "forbidI")
                .replaceAll(" select ", "forbidS")
                .replaceAll(" update ", "forbidU")
                .replaceAll(" delete ", "forbidD")
                .replaceAll(" and ", "forbidA")
                .replaceAll(" or ", "forbidO");
        if (!temp.equals(src)) {
            log.info("输入信息存在SQL攻击！"
                    + "原始输入信息-->【" + temp
                    + "】处理后信息-->【" + src + "】");
        }
        return src;
    }
}
