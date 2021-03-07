package io.github.lmikoto.railgun;

/**
 * @author liuyang
 * 2021/3/7 2:57 下午
 */
public interface SimpleName {

    /**
     * name
     * @return
     */
    String getName();

    /**
     * simple name
     * @return
     */
    default String getSimpleName(){
        return JavaUtils.getSimpleName(getName());
    }
}
