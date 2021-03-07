package io.github.lmikoto.railgun.model;

import io.github.lmikoto.railgun.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class Table extends Model {

    /**
     * 表名称
     */
    private String table;

    public Table() {}

    public Table(List<Field> fields) {
        setFields(fields);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        this.table = StringUtils.camelToUnderline(name);
    }


    public void setTable(String table) {
        this.table = table;
        super.setName(StringUtils.underlineToCamel(table, true));
    }
}
