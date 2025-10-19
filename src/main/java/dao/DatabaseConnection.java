package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.Config;

/**
 * Manages database connections using JDBC.
 */
public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = Config.get("db.url");
            String user = Config.get("db.user");
            String password = Config.get("db.password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
