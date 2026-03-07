package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private static final String URL =
            "jdbc:sqlserver://LAPTOP-9C3P5QST:61768;"
            + "databaseName=software_ayza_atgawez;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("[DB] Connection successful.");
        } catch (SQLException e) {
            System.out.println("[DB] Connection FAILED: " + e.getMessage());
            System.out.println("     SQLState : " + e.getSQLState());
            System.out.println("     ErrorCode: " + e.getErrorCode());
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public static void testConnection() {
        Connection conn = DBConnection.getInstance().getConnection();
        if (conn != null) {
            System.out.println("[DB] Database connection successful!");
        } else {
            System.out.println("[DB] Database connection failed.");
        }
    }

    public static void main(String[] args) {
        testConnection();
    }
}