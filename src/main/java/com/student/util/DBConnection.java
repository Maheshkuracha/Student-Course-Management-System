package com.student.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {

    public static Connection getConnection() {

        Connection connection = null;

        try {

            Properties props = new Properties();
            InputStream input = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            props.load(input);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}