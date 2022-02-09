package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectPostgresql {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DATABASE_USER_NAME = "postgres";
    private static final String DATABASE_PASSWORD = "nguyenhuuvu";
    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
    }
}
