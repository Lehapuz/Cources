package org.example.repository;

import lombok.Getter;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBResources {
    private static String URL;
    private static String USER;
    private static String PASS;
    private final static int POOL_SIZE = 5;
    @Getter
    private final static DBResources instance = new DBResources();


    public void getResource() throws IOException, SQLException {
        Properties properties = new Properties();
        String propertiesFileName = "connection.properties";
        properties.load(DBResources.class.getClassLoader().getResourceAsStream(propertiesFileName));
        URL = properties.getProperty("url");
        USER = properties.getProperty("username");
        PASS = properties.getProperty("password");
        DriverManager.registerDriver(new org.postgresql.Driver());
        }

    public String getURL() {
        return URL;
    }

    public String getUser() {
        return USER;
    }

    public String getPASS() {
        return PASS;
    }

    public int getPoolSize() {
        return POOL_SIZE;
    }
}
