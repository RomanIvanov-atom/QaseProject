package pages.base;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

@Log4j2
public class BasePage {

    protected WebDriver driver;
    protected static int short_timeout = 3;
    protected static int timeout = 10;
    protected static int long_timeout = 30;


    public void waitForDocumentReadyStateComplete() {
        try {
            WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(long_timeout));
            wait.until((ExpectedCondition<Boolean>) driverObject ->
                    Boolean.valueOf(((JavascriptExecutor) driver).
                            executeScript("return document.readyState === 'complete'").toString()));
        } catch (Exception ex) {
            log.error("Waiting for Ready State loaded was failed. Page didn't loaded");
        }
    }

    public void waitForDOMLoaded() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(short_timeout)).until(
                    (ExpectedCondition<Boolean>) driver -> Boolean.valueOf(((JavascriptExecutor) driver)
                            .executeScript("return window.performance.timing.loadEventEnd === 0").toString()));
        } catch (Exception ex) {
            // ignore
        }
        waitForDocumentReadyStateComplete();
    }
}
