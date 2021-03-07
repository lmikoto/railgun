package io.github.lmikoto.railgun.utils;

import io.github.lmikoto.railgun.model.FieldType;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class SqlParserUtils {

    /**
     * 未知字段类型
     */
    public static String UNKNOWN_FIELD = "UNKNOWN";

    /**
     * sqlType <-> javaType
     *
     * 如果要转javaType的枚举, 可以使用Types
     * @see Types
     */
    private static Map<String, FieldType> sqlTypes = new HashMap<>();
    static {
        sqlTypes.put(UNKNOWN_FIELD, FieldType.build(UNKNOWN_FIELD));
        sqlTypes.put("BIT", FieldType.build("Boolean"));
        sqlTypes.put("BOOL", FieldType.build("Boolean"));
        sqlTypes.put("BOOLEAN", FieldType.build("Boolean"));
        sqlTypes.put("TINYINT", FieldType.build("Integer", "Int"));
        sqlTypes.put("SMALLINT", FieldType.build("Integer", "Int"));
        sqlTypes.put("MEDIUMINT", FieldType.build("Integer", "Int"));
        sqlTypes.put("INT", FieldType.build("Integer", "Int"));
        sqlTypes.put("INTEGER", FieldType.build("Integer", "Int"));
        sqlTypes.put("REAL", FieldType.build("Float"));
        sqlTypes.put("FLOAT", FieldType.build("Double"));
        sqlTypes.put("DOUBLE", FieldType.build("Double"));
        sqlTypes.put("BIGINT", FieldType.build("Long"));
        sqlTypes.put("STRING", FieldType.build("String"));
        sqlTypes.put("CHAR", FieldType.build("String"));
        sqlTypes.put("VARCHAR", FieldType.build("String"));
        sqlTypes.put("TINYTEXT", FieldType.build("String"));
        sqlTypes.put("TEXT", FieldType.build("String"));
        sqlTypes.put("MEDIUMTEXT", FieldType.build("String"));
        sqlTypes.put("LONGTEXT", FieldType.build("String"));
        sqlTypes.put("SET", FieldType.build("String"));
        // java.sql.Date ?
        sqlTypes.put("DATE", FieldType.build("java.util.Date"));
        // java.sql.Timestamp ?
        sqlTypes.put("DATETIME", FieldType.build("java.util.Date"));
        // java.sql.Timestamp ?
        sqlTypes.put("TIMESTAMP", FieldType.build("java.util.Date"));
        // java.sql.Time ?
        sqlTypes.put("TIME", FieldType.build("java.util.Date"));
        sqlTypes.put("DECIMAL", FieldType.build("java.math.BigDecimal"));
        sqlTypes.put("BINARY", FieldType.build("Byte[]", "ByteArray"));
        sqlTypes.put("VARBINARY", FieldType.build("Byte[]", "ByteArray"));
        sqlTypes.put("BLOB", FieldType.build("java.sql.Blob"));
        sqlTypes.put("TINYBLOB", FieldType.build("java.sql.Blob"));
        sqlTypes.put("MEDIUMBLOB", FieldType.build("java.sql.Blob"));
        sqlTypes.put("LONGBLOB", FieldType.build("java.sql.Blob"));

        // oracle https://docs.oracle.com/cd/B19306_01/java.102/b14188/datamap.htm
        // 与上重复的忽略
        sqlTypes.put("CLOB", FieldType.build("java.sql.Clob"));
        sqlTypes.put("NCLOB", FieldType.build("java.sql.NClob"));
        sqlTypes.put("CHARACTER", FieldType.build("String"));
        sqlTypes.put("VARCHAR2", FieldType.build("String"));
        sqlTypes.put("NCHAR", FieldType.build("String"));
        sqlTypes.put("NVARCHAR2", FieldType.build("String"));
        sqlTypes.put("RAW", FieldType.build("Byte[]", "ByteArray"));
        sqlTypes.put("LONG RAW", FieldType.build("Byte[]", "ByteArray"));
        sqlTypes.put("BINARY_INTEGER", FieldType.build("Integer", "Int"));
        sqlTypes.put("NATURAL", FieldType.build("Integer", "Int"));
        sqlTypes.put("NATURALN", FieldType.build("Integer", "Int"));
        sqlTypes.put("PLS_INTEGER", FieldType.build("Integer", "Int"));
        sqlTypes.put("POSITIVE", FieldType.build("Integer", "Int"));
        sqlTypes.put("POSITIVEN", FieldType.build("Integer", "Int"));
        sqlTypes.put("SIGNTYPE", FieldType.build("Integer", "Int"));
        sqlTypes.put("DEC", FieldType.build("java.math.BigDecimal"));
        sqlTypes.put("NUMBER", FieldType.build("java.math.BigDecimal"));
        sqlTypes.put("NUMERIC", FieldType.build("java.math.BigDecimal"));
        sqlTypes.put("DOUBLE PRECISION", FieldType.build("Double"));
        sqlTypes.put("ROWID", FieldType.build("java.sql.RowId"));
        sqlTypes.put("UROWID", FieldType.build("java.sql.RowId"));
        sqlTypes.put("VARRAY", FieldType.build("java.sql.Array"));

        // sql server https://docs.microsoft.com/en-us/sql/connect/jdbc/using-basic-data-types
        // 与上重复的忽略
        // java.sql.Timestamp ?
        sqlTypes.put("DATETIME2", FieldType.build("java.util.Date"));
        // java.sql.Timestamp ?
        sqlTypes.put("SMALLDATETIME", FieldType.build("java.util.Date"));
        sqlTypes.put("IMAGE", FieldType.build("Byte[]", "ByteArray"));
        sqlTypes.put("MONEY", FieldType.build("java.math.BigDecimal"));
        sqlTypes.put("SMALLMONEY", FieldType.build("java.math.BigDecimal"));
        sqlTypes.put("NTEXT", FieldType.build("String"));
        sqlTypes.put("NVARCHAR", FieldType.build("String"));
        sqlTypes.put("UNIQUEIDENTIFIER", FieldType.build("String"));
        sqlTypes.put("UDT", FieldType.build("Byte[]", "ByteArray"));
        // sqlTypes.put("VARBINARY", build("Byte[]", "ByteArray"))
        sqlTypes.put("XML", FieldType.build("java.sql.SQLXML"));

        // postgresql https://www.postgresql.org/docs/9.5/datatype.html
        sqlTypes.put("JSON", FieldType.build("String"));
        sqlTypes.put("JSONB", FieldType.build("Byte[]", "ByteArray"));
        // sqlTypes.put("XML", build("String"));
        sqlTypes.put("UUID", FieldType.build("String"));
        sqlTypes.put("CHARACTER VARYING", FieldType.build("String"));
        sqlTypes.put("CHARACTER VARYING[]", FieldType.build("String[]", "Array<String>"));
        sqlTypes.put("TEXT[]", FieldType.build("String[]", "Array<String>"));
        sqlTypes.put("INTEGER[]", FieldType.build("Integer[]", "Array<Int>"));
        sqlTypes.put("HSTORE", FieldType.build("java.util.Map<String, String>", "Map<String, String>"));
    }

    /**
     * 此处就不转成java的Types了, 直接由columnName去匹配字段对应的类型
     *
     * @return 对应字段的java类型
     */
    public static FieldType getFieldType(String typeName) {
        if (StringUtils.isBlank(typeName)) {
            sqlTypes.get(UNKNOWN_FIELD);
        }
        FieldType fieldType = sqlTypes.get(typeName.trim().toUpperCase());
        if (fieldType == null) {
            return sqlTypes.get(UNKNOWN_FIELD);
        }
        return fieldType;
    }

    /**
     * 根据Types获取字段类型
     *
     * @see Types
     * @return 对应字段的java类型
     */
    public static FieldType getFieldType(Integer sqlType) {
        FieldType fieldType = sqlTypes.get(UNKNOWN_FIELD);
        if (sqlType == null) {
            return fieldType;
        }

        // https://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/mapping.html
        if (sqlType == Types.INTEGER) {
            fieldType = sqlTypes.get("INTEGER");
        } else if (sqlType == Types.VARCHAR) {
            fieldType = sqlTypes.get("STRING");
        } else if (sqlType == Types.CHAR) {
            fieldType = sqlTypes.get("STRING");
        } else if (sqlType == Types.LONGVARCHAR) {
            fieldType = sqlTypes.get("STRING");
        } else if (sqlType == Types.NVARCHAR) {
            fieldType = sqlTypes.get("STRING");
        } else if (sqlType == Types.NCHAR) {
            fieldType = sqlTypes.get("STRING");
        } else if (sqlType == Types.LONGNVARCHAR) {
            fieldType = sqlTypes.get("STRING");
        } else if (sqlType == Types.NUMERIC) {
            fieldType = sqlTypes.get("DECIMAL");
        } else if (sqlType == Types.DECIMAL) {
            fieldType = sqlTypes.get("DECIMAL");
        } else if (sqlType == Types.BIT) {
            fieldType = sqlTypes.get("BOOLEAN");
        } else if (sqlType == Types.BOOLEAN) {
            fieldType = sqlTypes.get("BOOLEAN");
        } else if (sqlType == Types.TINYINT) {
            fieldType = sqlTypes.get("INTEGER");
        } else if (sqlType == Types.SMALLINT) {
            fieldType = sqlTypes.get("INTEGER");
        } else if (sqlType == Types.BIGINT) {
            fieldType = sqlTypes.get("BIGINT");
        } else if (sqlType == Types.REAL) {
            fieldType = sqlTypes.get("REAL");
        } else if (sqlType == Types.FLOAT) {
            fieldType = sqlTypes.get("FLOAT");
        } else if (sqlType == Types.DOUBLE) {
            fieldType = sqlTypes.get("DOUBLE");
        } else if (sqlType == Types.DATE) {
            // java.sql.Date ?
            fieldType = sqlTypes.get("DATE");
        } else if (sqlType == Types.TIME) {
            // java.sql.Time ?
            fieldType = sqlTypes.get("TIME");
        } else if (sqlType == Types.TIMESTAMP) {
            // java.sql.Timestamp ?
            fieldType = sqlTypes.get("TIMESTAMP");
        } else if (sqlType == Types.BINARY
                || sqlType == Types.VARBINARY) {
            fieldType = sqlTypes.get("BINARY");
        } else if (sqlType == Types.CLOB) {
            fieldType = sqlTypes.get("CLOB");
        } else if (sqlType == Types.BLOB
                || sqlType == Types.LONGVARBINARY) {
            fieldType = sqlTypes.get("BLOB");
        } else {
            // DISTINCT, ARRAY, STRUCT, REF, JAVA_OBJECT.
            return fieldType;
        }
        return fieldType;
    }

}
