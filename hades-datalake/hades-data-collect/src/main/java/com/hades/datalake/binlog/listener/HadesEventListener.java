package com.hades.datalake.binlog.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.hades.datalake.binlog.metastore.TableInfoKeeper;
import com.hades.datalake.binlog.model.ColumnInfo;
import com.hades.datalake.binlog.model.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HadesEventListener implements BinaryLogClient.EventListener {
    private static final Logger logger = LoggerFactory.getLogger(HadesEventListener.class);

    public HadesEventListener() {

    }

    @Override
    public void onEvent(Event event) {
        if (event == null) {
            logger.error("binlog event is null");
            return;
        }
        EventType eventType = event.getHeader().getEventType();

        // 如果 表结构发生变化 则删除原表结构 增加新结构
        if(eventType == EventType.TABLE_MAP) {
            // tableMapEventData
            TableMapEventData tableMapEventData = event.getData();
            TableInfoKeeper.saveTableIdMap(tableMapEventData);
            logger.trace("TABLE_MAP_EVENT:tableId : {} ", tableMapEventData.getTableId());
        } else if (EventType.isWrite(eventType)) {

        } else if (EventType.isUpdate(eventType)) {

        } else if (EventType.isDelete(eventType)) {

        }
    }

    // 私有函数 mysql发生新增写入的时候进行处理
    private void writerEventHandler(Event event) {
        WriteRowsEventData writeRowsEventData = event.getData();
        long tableId = writeRowsEventData.getTableId();
        logger.trace("TABLE_MAP_EVENT:tableId : {} ", tableId);

        // 获取对应的表信息 TableId为唯一主键
        TableInfo tableInfo = TableInfoKeeper.getTableInfo(tableId);
        String tableName = tableInfo.getTableName();
        String databaseName = tableInfo.getDatabaseName();


        List<Serializable[]> rows = writeRowsEventData.getRows();

        // TODO 为什么选择CharSequence
        for(Serializable[] row : rows) {
            Map<CharSequence, CharSequence>  afterMap = getMap(row, databaseName, tableName);
        }
    }

    // 私有函数

    /**
     * @param row
     * @param databaseName
     * @param tableName
     * @return
     */
    private Map<CharSequence, CharSequence> getMap(Object[] row, String databaseName, String tableName) {

        // TODO 为什么使用CharSequence 为什么使用HashMap 而不使用ConcurrentHashMap
        Map<CharSequence, CharSequence> map = new HashMap<>();
        if(row == null || row.length == 0) {
            return null;
        }

        // 后期加上数据表类型如Hive
        String fullName = databaseName + "." + tableName;
        //
        List<ColumnInfo> columnInfoList = TableInfoKeeper.getColumns(fullName);
        if (columnInfoList == null) {
            return null;
        }
        if (columnInfoList.size() != row.length) {
            // 不一致进行更新
            TableInfoKeeper.refreshColumnsMap();
            if (columnInfoList.size() != row.length) {
                logger.warn("列信息不符合");
                return null;
            }
        }
        for (int i = 0; i < columnInfoList.size(); i++) {
            map.put(columnInfoList.get(i).getName(), row[i] == null ? "" : row[i].toString());
        }
        return map;
    }
}
