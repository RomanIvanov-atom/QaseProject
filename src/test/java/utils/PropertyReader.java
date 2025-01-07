package utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static utils.constants.Constants.PASSWORD;
import static utils.constants.Constants.USERNAME;

@Log4j2
public final class PropertyReader {
    private static String propertiesPath = "/config.properties";
    private static volatile Properties properties;
    private static InputStream inputStream;
    private static final String env = System.getProperty("ENV", "local");

    private PropertyReader() {
    }

    private static String getCorrectPath() {
        if (propertiesPath.charAt(0) != '/')
            propertiesPath = "/" + propertiesPath;
        return propertiesPath;
    }

    public static Properties readProperties(String env) {
        properties = new Properties();
        log.info("!!!DEBUGLOG. In fact env = " + env);
        try {
            if ("ci".equalsIgnoreCase(env)) {
                propertiesPath = "/config.properties.TEMPLATE";
                log.info("!!!LOG!!! Сработал config.properties.TEMPLATE");
                log.info("login is: " + USERNAME);
                log.info("pass is: " + PASSWORD);
            } else {
                propertiesPath = "/config.properties";
                log.info("!!!LOG!!! Сработал config.properties");
            }

            inputStream = PropertyReader.class.getResourceAsStream(getCorrectPath());
            if (inputStream != null)
                properties.load(inputStream);
        } catch (Exception ex) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (properties.getProperty("config_file") != null) {
            Properties additionalProperties = getProperties(properties.getProperty("config_file"));
            properties.putAll(additionalProperties);
        }
        return properties;
    }

    private static Properties loadProperties() {
        return properties != null ? properties : readProperties(env);
    }

    public static Properties getProperties(String path) {
        propertiesPath = path;
        return readProperties(env);
    }

    public static String getProperty(String propertyName) {
        return readProperties(env).getProperty(propertyName);
    }
}
