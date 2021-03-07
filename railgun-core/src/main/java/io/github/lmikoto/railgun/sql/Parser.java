package io.github.lmikoto.railgun.sql;

import io.github.lmikoto.railgun.model.Table;

import java.util.List;

public interface Parser {

    /**
     * 将输入的sql语句解析成多个 Table 对象
     * @param sqls sql 语句, 分号分隔
     *
     * @return {@link Table}
     */
    List<Table> parseSQLs(String sqls);
}
