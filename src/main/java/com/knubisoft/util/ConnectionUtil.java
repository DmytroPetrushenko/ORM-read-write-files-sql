package com.knubisoft.util;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_PROPERTIES = "database/db.properties";

    @SneakyThrows
    public Connection getConnection() {
        Properties dbProperties = getDBProperties();
        Class.forName(dbProperties.getProperty("db.driver"));
        return DriverManager.getConnection(dbProperties.getProperty("db.url"),
                dbProperties.getProperty("db.user"), dbProperties.getProperty("db.password"));
    }

    @SneakyThrows
    private Properties getDBProperties() {
        URL url = getClass().getClassLoader().getResource(DB_PROPERTIES);
        FileInputStream fileInputStream = new FileInputStream(Objects.requireNonNull(url).getFile());
        Properties properties = new Properties();
        properties.load(fileInputStream);
        return properties;
    }

}
