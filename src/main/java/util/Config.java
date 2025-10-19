package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration utility for loading database properties.
 */
public class Config {
    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("resources/db_config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load configuration from resources/db_config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
