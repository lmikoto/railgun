package io.github.lmikoto.railgun.utils;

import java.io.File;

/**
 * @author liuyang
 * 2021/1/3 2:38 下午
 */
public class PathUtils {

    public static String applyPath(String path) {
        if (StringUtils.isBlank(path) || path.lastIndexOf(File.separator) == path.length() - 1) {
            return path;
        }
        return path + File.separator;
    }
}
