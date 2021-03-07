package io.github.lmikoto.railgun.utils;

/**
 * @author liuyang
 * 2021/1/3 7:48 下午
 */
public class JavaClassUtils {

    public static String getClassName(String qualifiedName){
        int i = qualifiedName.lastIndexOf(".");
        return qualifiedName.substring(i + 1);
    }
}
