package com.company;

import java.sql.*;
import java.util.Properties;

public class Connect {

    private String userName = "root";
    private String password = "1234";
    private String connectionUrl = "jdbc:mysql://localhost:3305/test";
    private Connection connection = null;

    public Connect() throws SQLException {
        try {
            Properties p = new Properties();
            p.setProperty(userName, password);
            p.setProperty("useUnicode", "true");
            p.setProperty("characterEncoding", "cp1251");

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl, p);
            System.out.println("Connected! ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                    System.out.println("Database connection terminated");
//                }
//            } catch (Exception e) {
//            }
//        }
    }

    public Connection getConnection() {
        return connection;
    }
}

