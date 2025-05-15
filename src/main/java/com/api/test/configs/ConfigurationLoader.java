package com.api.test.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {

  private static Properties properties = new Properties();

  static {
    String env = System.getProperty("prod", "dev"); // default to 'qa'
    String fileName = "application-" + env + ".properties";

    try (InputStream input = ConfigurationLoader.class.getClassLoader().getResourceAsStream(fileName)) {
      if (input == null) {
        throw new RuntimeException(fileName + " not found in classpath");
      }
      properties.load(input);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to load " + fileName, ex);
    }
  }

  public static String getProperty(String key) {
    String value = properties.getProperty(key);
    if (value == null) {
      throw new RuntimeException("Property '" + key + "' not found in loaded configuration file");
    }
    return value;
  }
}