package io.github.lmikoto.railgun.model;

import io.github.lmikoto.railgun.utils.SqlParserUtils;
import io.github.lmikoto.railgun.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Types;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper=false)
public class Field extends BaseMappedProperty implements Serializable{

    private static final long serialVersionUID = -7928412682947631640L;

    /**
     * 属性类型
     */
    private String fieldType;

    /**
     * 数据库字段名
     */
    private String column;

    /**
     * 数据库字段类型
     */
    private String columnType;

    /**
     * 数据库字段类型
     * @see java.sql.Types
     */
    private Integer sqlType;

    /**
     * 数据库字段长度
     */
    private String columnSize;

    /**
     * 备注
     */
    private String comment;

    /**
     * 注解
     */
    private Map<String,Map<String,Object>> ans;

    public void setName(String name) {
        this.name = name;
        this.column = StringUtils.camelToUnderline(this.name);
    }

    public void setColumn(String column) {
        this.column = column;
        this.name = StringUtils.underlineToCamel(this.column);
    }

    public void setColumnType(String columnType) {
        // 默认sqlType为NULL类型
       this.setColumnType(columnType, sqlType == null ? Types.NULL : sqlType);
    }
    public void setColumnType(String columnType, Integer sqlType) {
        this.columnType = columnType;
        this.sqlType = sqlType;

        FieldType fieldType = SqlParserUtils.getFieldType(columnType);
        if (SqlParserUtils.UNKNOWN_FIELD.equals(fieldType.getJavaType())) {
            fieldType = SqlParserUtils.getFieldType(sqlType);
        }
        this.fieldType = fieldType.getJavaType();
    }

    public void setSqlType(String sqlType) {
        try {
            this.sqlType = Integer.parseInt(sqlType);
        } catch (Exception e) { // ignore error
            this.sqlType = Types.NULL;
        }
    }
}
