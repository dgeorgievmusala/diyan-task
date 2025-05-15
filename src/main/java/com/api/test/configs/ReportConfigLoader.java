package com.api.test.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReportConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ReportConfigLoader.class.getClassLoader().getResourceAsStream("test-reporter.properties")) {
            if (input == null) throw new RuntimeException("test-reporter.properties not found");
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading test-reporter.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
