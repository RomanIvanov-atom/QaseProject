package tests.base;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.LoginPage;
import tests.base.annotations.Login;
import utils.constants.Constants;
import utils.listeners.TestListener;

import java.lang.reflect.Method;
import java.time.Duration;

@Log4j2
@Listeners(TestListener.class)
public abstract class BaseTest {

    protected WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod
    @Step("Open browser")
    protected void setup(@Optional("chrome") String browser, ITestContext context) {
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
        context.setAttribute("driver", driver);
    }


    @BeforeMethod(dependsOnMethods = {"setup", "openPage"})
    @Step("Doing login as precondition")
    protected void executePreconditions(Method method) {
        try {
            if (!method.isAnnotationPresent(Login.class) || method.getAnnotation(Login.class).doLogIn()) {
                String username = Constants.USERNAME;
                String password = Constants.PASSWORD;
                if (method.isAnnotationPresent(Login.class)) {
                    Login la = method.getAnnotation(Login.class);
                    if (!(la.login().isEmpty() && la.password().isEmpty())) {
                        username = la.login();
                        password = la.password();
                    }
                }
                log.debug("Trying to log in");
                new LoginPage(driver).doLogin(username, password);
            }
        } catch (Exception ex) {
            log.error("UI test's preconditions method was failed: " + ex.getMessage());
            log.error(ex.getMessage());
        }
    }

    private <T extends ChromiumOptions<T>> void setArgumentsForHeadless(T options) {
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
    }

    private boolean isHeadless() {
        return "headless".equals(System.getProperty("mbrowser"));
    }

    @AfterMethod(alwaysRun = true)
    @Step("Close browser")
    protected void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ex) {
                log.error("After method was failed: " + ex.getMessage());
            }
        }
    }
}
