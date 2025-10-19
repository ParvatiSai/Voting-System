package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Config;

/**
 * Manages database connections using JDBC.
 */
public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static Connection connection;

    static {
        // Explicitly load MySQL JDBC driver once (optional for modern JDBC but helpful)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.fine("MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            // Log and proceed: DriverManager may still resolve via service loader if on classpath
            LOGGER.log(Level.WARNING, "MySQL JDBC Driver not found on classpath: {0}", e.getMessage());
        }
    }

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
                LOGGER.log(Level.WARNING, "Error closing DB connection", e);
            }
        }
    }
}
