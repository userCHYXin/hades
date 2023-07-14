package com.hades.datalake.binlog.Factory;

/**
 * 工厂模式 创建binlog listener实例
 * @author hanjiajun02
 * @date 2023/7/13 16:10
 */
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.hades.datalake.binlog.listener.HadesEventListener;
import com.hades.datalake.binlog.metastore.MysqlConnection;

public class BinLogFactory {

    public String host;
    public int port;
    public String user;
    public String password;
    //public String DN_Table;

    public BinLogFactory(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        //this.DN_Table = DN_Table;
    }

    public BinaryLogClient getBinlogClient(){
        MysqlConnection.setConnection(host, port, user, password);
        return new BinaryLogClient(host,port,user,password);
    }

    public HadesEventListener getEventListener(){
        return new HadesEventListener();
    }

   /*public Filter getFilter(){
        return new Filter(BinlogTools.selectedPart(DN_Table));
    }*/
}
