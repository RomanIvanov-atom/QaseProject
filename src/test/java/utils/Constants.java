package utils;

public class Constants {

    // Login - constants
    public static final String USERNAME = PropertyReader.getProperty("username");
    public static final String PASSWORD = PropertyReader.getProperty("password");

    // QASE - urls
    public static final String BASE_API_URL = PropertyReader.getProperty("qase.base.api.url");
    public static final String API_TOKEN = PropertyReader.getProperty("qase.api.token");


    public static final String LOGIN_BASE_URL = PropertyReader.getProperty("qase.base.login.url");
    public static final String PROJECTS_BASE_URL = PropertyReader.getProperty("qase.base.projects.url");
}
