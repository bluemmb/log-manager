package org.me.api;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getConnection() throws Exception
    {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        // TODO : Use .env
        String host = "localhost";
        String port = "3306";
        String database = "log_manager";
        String username = "java";
        String password = "password";

        String connectionString = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
        return DriverManager.getConnection(connectionString, username, password);
    }
}
