package org.me.core.Services;

import org.me.core.DataObjects.LogData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class MysqlService {

    private final Connection connection;

    public MysqlService(Connection connection) {
        this.connection = connection;
    }

    public void storeLogData(String component, String key, LogData logData) throws SQLException {
        // TODO: Batch Insert
        PreparedStatement prepareStatement = connection.prepareStatement(
                "insert into logs (`key`, `component`, `logdatetime`, `type`, `threadName`, `className`, `message`, `created_at`)" +
                        " values (?, ?, ?, ?, ?, ?, ?, ?)" +
                        " on duplicate key update `key`=`key`;"
        );

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        prepareStatement.setString(1, key);
        prepareStatement.setString(2, component);

        prepareStatement.setString(3, dateFormat.format(logData.date));
        prepareStatement.setString(4, logData.type);
        prepareStatement.setString(5, logData.threadName);
        prepareStatement.setString(6, logData.className);
        prepareStatement.setString(7, logData.message);

        prepareStatement.setString(8, dateFormat.format(new java.util.Date()));

        prepareStatement.executeUpdate();
    }
}
