package com.hades.datalake.binlog.metastore;

import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.hades.datalake.binlog.model.ColumnInfo;
import com.hades.datalake.binlog.model.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hanjiajun02
 * @date 2023/7/13 20:02
 */
public class TableInfoKeeper {
    private static final Logger logger = LoggerFactory.getLogger(MysqlConnection.class);

    // concurrentHashMap
    // concurrentHashMap 具体介绍 https://crossoverjie.top/2018/07/23/java-senior/ConcurrentHashMap/
    private static Map<Long, TableInfo> tableIdMap = new ConcurrentHashMap<Long, TableInfo>();

    // fullname columninfolist
    private static Map<String, List<ColumnInfo>> columnsMap = new ConcurrentHashMap<>();

    static {
        // 获取列的名称
        columnsMap = MysqlConnection.getColumns();
    }

    // 表结构发生改变，删除原来的表，添加新表
    public static void saveTableIdMap(TableMapEventData tableMapEventData) {
        long tableId = tableMapEventData.getTableId();
        tableIdMap.remove(tableId);

        TableInfo table  = new TableInfo();
        table.setDatabaseName(tableMapEventData.getDatabase());
        table.setTableName(tableMapEventData.getTable());
        table.setFullName(tableMapEventData.getDatabase() + "." + tableMapEventData.getTable());

        tableIdMap.put(tableId, table);
    }

    // 很多操作都是并发执行的，会不会导致数据不一致 线程安全性应该如何保证
    public static TableInfo getTableInfo(long tableId) {
        return tableIdMap.get(tableId);
    }

    // 获取所有列信息
    public static List<ColumnInfo> getColumns(String fullName) {
        return columnsMap.get(fullName);
    }

    // 要加锁进行处理
    public static synchronized void refreshColumnsMap() {
        Map<String, List<ColumnInfo>> map = MysqlConnection.getColumns();
        if (map.size() > 0) {
            // 更新columnsMap  columnsMap.get(fullName)里内容也修改
            columnsMap = map;
        } else {
            logger.error("refresh columnsMap error ");
        }
    }

}
