package com.hades.datalake.binlog.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hanjiajun02
 * 自定义Hades消息监听器
 * 监听二进制文件并根据监听的文件获取数据库修改
 * @date 2023/7/13 19:22
 */

public class HadesListener implements BinaryLogClient.EventListener {

    private static final Logger logger = LoggerFactory.getLogger(HadesListener.class);

    // 空构造函数
    public HadesListener() {

    }

    @Override
    public void onEvent(Event event) {
        if (event == null) {
            logger.error("binlog event is Null");
            return;
        }

        // 获取事件类型
        EventType eventType = event.getHeader().getEventType();



    }
}
