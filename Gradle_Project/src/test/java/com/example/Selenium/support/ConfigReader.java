package com.example.Selenium.support;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis =
                     new FileInputStream("src/test/resources/config.properties")) {
            properties.load(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}