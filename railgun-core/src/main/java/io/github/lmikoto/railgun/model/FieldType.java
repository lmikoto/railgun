package io.github.lmikoto.railgun.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FieldType implements Serializable {
    private static final long serialVersionUID = -4088740233253463308L;

    /**
     * java类型
     */
    private String javaType;

    /**
     * kotlin类型
     */
    private String kotlinType;

    /**
     * 创建对象
     */
    public static FieldType build(String javaType, String kotlinType) {
        return new FieldType(javaType, kotlinType);
    }
    public static FieldType build(String type) {
        return build(type, type);
    }
}
