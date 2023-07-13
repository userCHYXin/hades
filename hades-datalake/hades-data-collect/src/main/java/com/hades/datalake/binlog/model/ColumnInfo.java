package com.hades.datalake.binlog.model;

/**
 * @author hanjiajun02
 * @date 2023/7/13 17:16
 */

// 列信息
public class ColumnInfo {

    // 获取MySQL列信息
    private String name;
    private String type;
    public ColumnInfo(String name,String type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
