package io.github.lmikoto.railgun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author liuyang
 * 2021/1/3 2:10 下午
 */
@Data
public class Model extends BaseMappedProperty implements Serializable {

    /**
     * 字段
     */
    private List<Field> fields;

    /**
     * 注解
     */
    private List<Annotation> annotations;

    /**
     * 方法
     */
    private List<Method> methods;

    /**
     * 注解
     */
    // todo 干掉
    private Map<String,Map<String,Object>> ans;
}
