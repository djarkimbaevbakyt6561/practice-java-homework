package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConfig {

    private final static String url = "jdbc:postgresql://localhost:5432/jdbc_java-12";
    private final static String username = "postgres";
    private final static String password = "1234";

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
