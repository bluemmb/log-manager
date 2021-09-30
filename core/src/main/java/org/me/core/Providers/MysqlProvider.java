package org.me.core.Providers;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Services.MysqlService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlProvider extends AbstractModule {

    @Inject
    @Provides
    @Singleton
    public Connection providesMysqlConnection(Dotenv dotenv) throws SQLException {
        String host = dotenv.get("MYSQL.HOST");
        String port = dotenv.get("MYSQL.PORT");
        String database = dotenv.get("MYSQL.DATABASE");
        String username = dotenv.get("MYSQL.USERNAME");
        String password = dotenv.get("MYSQL.PASSWORD");

        String connectionString = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Inject
    @Provides
    @Singleton
    public MysqlService providesMysqlService(Connection connection) {
        return new MysqlService(connection);
    }
}
