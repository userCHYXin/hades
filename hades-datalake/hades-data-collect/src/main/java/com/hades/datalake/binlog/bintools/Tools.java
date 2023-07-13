package com.hades.datalake.binlog.bintools;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author hanjiajun02
 * @date 2023/7/13 15:39
 */
public class Tools {

    // 工具类 根据DataTableNames 和 KafkaTopics建立映射
    public static HashMap<String,String> readTopic(String DataTableNames, String KafkaTopics){
        HashMap<String, String> topicMap = new HashMap<>();
        String[] dtList = DataTableNames.split(" ");
        String[] topicList = KafkaTopics.split(" ");

        if(!(topicList.length == dtList.length)){
            return null;
        } else{
            for(int i = 0; i < dtList.length; i++){
                topicMap.put(dtList[i], topicList[i]);
            }
        }
        return topicMap;
    }
}
