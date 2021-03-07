package io.github.lmikoto.railgun.utils;

import java.util.Collection;

/**
 * @author liuyang
 * 2021/3/7 12:53 下午
 */
public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> collection){
        return !isNotEmpty(collection);
    }

    public static <T> boolean isNotEmpty(Collection<T> collection){
        return collection != null && collection.size() != 0;
    }
}
