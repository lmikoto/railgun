package io.github.lmikoto.railgun;

/**
 * @author liuyang
 * 2021/3/7 2:16 下午
 */
public class JavaUtils {

    public static String getSimpleName(String name){
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String getPackageName(String name){
        return name.substring(0,name.lastIndexOf("."));
    }
}
