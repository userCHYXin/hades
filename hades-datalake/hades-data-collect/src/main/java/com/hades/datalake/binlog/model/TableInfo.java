package com.hades.datalake.binlog.model;

import lombok.Data;

/**
 * @author hanjiajun02
 * 表信息
 * @date 2023/7/13 20:03
 */
@Data
public class TableInfo {
    private String databaseName;

    private String tableName;

    private String fullName;

    // 重写equals方法
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || this.getClass()!=o.getClass()) {
            return false;
        }
        TableInfo tableInfo = (TableInfo)o;
        if(!this.databaseName.equals(tableInfo.getDatabaseName())) {
            return false;
        }
        if(!this.tableName.equals(tableInfo.getTableName())) {
            return false;
        }
        if(!this.fullName.equals(tableInfo.getFullName())) {
            return false;
        }
        return true;
    }

    // 重写hashCode方法
    @Override
    public int hashCode() {
        int result = this.tableName.hashCode();
        result = 31*result + this.databaseName.hashCode();
        result = 31*result + this.fullName.hashCode();
        return result;
    }
}
