package tests.base;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.SkipException;
import org.testng.annotations.*;
import utils.listeners.TestListener;

import java.lang.reflect.Method;
import java.time.Duration;

@Log4j2
@Listeners(TestListener.class)
public abstract class BaseTest {

    protected WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            if (isHeadless()) {
                setArgumentsForHeadless(options);
            }
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("start-maximized");
            if (isHeadless()) {
                setArgumentsForHeadless(options);
            }
            driver = new EdgeDriver(options);
        } else {
            log.error("Parameter 'browser' wasn't found");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private <T extends ChromiumOptions<T>> void setArgumentsForHeadless(T options) {
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
    }

    private boolean isHeadless() {
        return "headless".equals(System.getProperty("mbrowser"));
    }

//    @BeforeMethod(dependsOnMethods = {"setUp", "openPage"})
//    protected void executePreconditions(Method method) {
//        try {
//            if (!method.isAnnotationPresent(Login.class) || method.getAnnotation(Login.class).doLogIn()) {
//                String username = EnvConstants.ADMIN_USERNAME;
//                String password = EnvConstants.ADMIN_PASSWORD;
//                if (method.isAnnotationPresent(Login.class)) {
//                    Login la = method.getAnnotation(Login.class);
//                    if (!(la.login().isEmpty() && la.password().isEmpty())) {
//                        username = la.login();
//                        password = la.password();
//                    }
//                }
//                log.debug("Trying to log in");
//                new LoginPage(driver).doLogin(username, password);
//            }
//        } catch (Exception ex) {
//            log.error("UI test's preconditions method was failed: " + ex.getMessage());
//            throw new SkipException(ex.getMessage());
//        }
//    }

    @AfterMethod(alwaysRun = true)
    protected void environmentConfiguratorAfterMethod(Method method) {
        try {
            driver.quit();
        } catch (Exception ex) {
            log.error("After method was failed: " + ex.getMessage());
            throw new SkipException(ex.getMessage());
        }
    }
}
