package io.github.lmikoto.railgun.sql;

import io.github.lmikoto.railgun.model.Table;
import io.github.lmikoto.railgun.utils.StringUtils;

import java.util.List;

public abstract class AbstractParser implements Parser {

    @Override
    public List<Table> parseSQLs(String sqls) {
        return null;
    }

    /**
     * 去除一些特殊的引号字符
     */
    protected String removeQuotes(String input) {
        if (StringUtils.isBlank(input)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (ch == '`' || ch == '\'' || ch == '\"') {
                continue;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * 获取第一个元素或者null
     */
    protected <T> T firstOrNull(List<T> lists) {
        if (lists == null || lists.isEmpty()) {
            return null;
        }
        return lists.get(0);
    }
}
