package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration utility for loading database properties.
 */
public class Config {
    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("resources/db_config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
