package io.github.lmikoto.railgun.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author liuyang
 * 2020/12/5 10:40 下午
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {


    public static final char UNDERLINE = '_';

    /**
     * 下划线字符串修改为驼峰命名
     */
    public static String underlineToCamel(String param) {
        return underlineToCamel(param, false);
    }

    /**
     * 下划线字符串修改为驼峰命名
     * @param firstUpper 首字符是否大写
     */
    public static String underlineToCamel(String param, boolean firstUpper) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                if (i == 0 && firstUpper) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰名称修改为下划线分隔的字符串
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        stringWriter.append("\n").append("BuildNumber:");
        return stringWriter.toString();
    }
}
