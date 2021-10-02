package org.me.core.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

public class MysqlService {

    private final Connection connection;

    public MysqlService(Connection connection) {
        this.connection = connection;
    }

    public boolean storeAlert(String ruleName, String component, String description) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(
                    "insert into alerts (`ruleName`, `component`, `description`, `created_at`)" +
                            " values (?, ?, ?, ?)"
            );

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            prepareStatement.setString(1, ruleName);
            prepareStatement.setString(2, component);
            prepareStatement.setString(3, description);
            prepareStatement.setString(4, dateFormat.format(new java.util.Date()));

            prepareStatement.executeUpdate();
            return true;
        }
        catch (Exception e) {
            System.out.println("Database Error : " + e.getMessage());
            return false;
        }
    }
}
