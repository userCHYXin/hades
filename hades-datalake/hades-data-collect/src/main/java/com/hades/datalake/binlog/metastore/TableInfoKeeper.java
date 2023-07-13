package com.hades.datalake.binlog.metastore;

import com.hades.datalake.binlog.model.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hanjiajun02
 * @date 2023/7/13 20:02
 */
public class TableInfoKeeper {
    private static final Logger logger = LoggerFactory.getLogger(MysqlConnection.class);

    // ConcurrentHashMap 
    private static Map<Long, TableInfo> tabledIdMap = new ConcurrentHashMap<Long, TableInfo>();

}
