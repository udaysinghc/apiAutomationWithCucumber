package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

  private static Properties properties = new Properties();

  static {
    try {
      FileInputStream configStream = new FileInputStream("src/test/resources/config.properties");
      properties.load(configStream);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to load configuration file.");
    }
  }

  public static String getProperty(String key) {
    return properties.getProperty(key);
  }

  public static String getBaseUrl() {
    return getProperty("baseUrl");
  }

  public static String getApiKey() {
    return getProperty("apiKey");
  }
}
