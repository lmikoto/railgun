package io.github.lmikoto.railgun;

import io.github.lmikoto.railgun.utils.CollectionUtils;
import lombok.Data;

import java.util.*;

/**
 * @author lmikoto
 */
@Data
public class SimpleClass implements SimpleName {

    private Set<String> imports;

    /**
     * class name
     */
    private String name;

    private List<SimpleAnnotation> annotations;

    private List<SimpleClass> extend;

    private List<SimpleClass> impl;

    private List<SimpleMethod> methods;

    private LinkedHashMap<String,SimpleClass> fields;

    private List<String> modifiers;

    public Set<String> getImports(){
        if(CollectionUtils.isEmpty(imports)){
            imports = new HashSet<>();
        }
        return imports;
    }

    /**
     * @author liuyang
     * 2021/3/7 1:02 下午
     */
    @Data
    public static class SimpleAnnotation implements SimpleName {

        /**
         * 全路径名
         */
        private String name;

        private String expr;
    }

    @Data
    public static class SimpleMethod {

        private String name;

        private SimpleClass type;

        private List<SimpleAnnotation> annotations;

        private LinkedHashMap<String,SimpleClass> params;

        private List<String> line;
    }
}
