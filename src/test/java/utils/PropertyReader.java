package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
        try {
            if ("ci".equalsIgnoreCase(env)) {
                propertiesPath = "/config.properties.TEMPLATE";
            } else {
                propertiesPath = "/config.properties";
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
