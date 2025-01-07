package pages.base;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public abstract class BasePage {

    protected WebDriver driver;
    protected static int short_timeout = 3;
    protected static int timeout = 10;
    protected static int long_timeout = 30;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected abstract void pageLoading();

    /**
     * Wait until page document loading is complete
     */
    private void waitForDocumentReadyStateComplete() {
        try {
            WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(long_timeout));
            wait.until((ExpectedCondition<Boolean>) driverObject ->
                    Boolean.valueOf(((JavascriptExecutor) driver).
                            executeScript("return document.readyState === 'complete'").toString()));
        } catch (Exception ex) {
            log.error("Waiting for Ready State loaded was failed. Page didn't loaded");
        }
    }

    /**
     * Wrap XPath string with index
     */
    private String wrapXPathWithIndex(String xpath, int index) {
        return "(" + xpath + ")[" + (index + 1) + "]";
    }

    /**
     * Returns the class field name for xpath
     */
    private String getXpathName(String xpath) {
        String name = "";
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields)
                try {
                    f.setAccessible(true);
                    if (f.get(this).equals(xpath)) {
                        name = f.getName();
                    }
                } finally {
                    f.setAccessible(false);
                }
            return name;
        } catch (Exception ex) {
            return name;
        }
    }

    /**
     * Wait until page document loading is complete
     */
    public void waitForDOMLoaded() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(
                    (ExpectedCondition<Boolean>) driver -> Boolean.valueOf(((JavascriptExecutor) Objects.requireNonNull(driver))
                            .executeScript("return window.performance.timing.loadEventEnd === 0").toString()));
        } catch (Exception ex) {
            // ignore
        }
        waitForDocumentReadyStateComplete();
    }

    /**
     * Return element visibility state
     */
    private static boolean isElementVisible(WebDriver driver, String xpath, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return element.isDisplayed();
        } catch (TimeoutException e) {
            log.info("Element not visible within timeout: " + xpath);
            return false;
        } catch (Exception e) {
            log.error("An error occurred: " + e.getMessage());
            return false;
        }
    }

    public boolean isVisible(String xpath, int index, int timeout) {
        return isElementVisible(driver, wrapXPathWithIndex(xpath, index), timeout);
    }

    public boolean isVisible(String xpath, int index) {
        return isVisible(xpath, index, short_timeout);
    }

    public boolean isVisible(String xpath) {
        return isVisible(xpath, 0, timeout);
    }

    /**
     * Return web element text
     */
    public String getText(String xpath, int index, int timeout) throws Exception {
        try {
            WebElement webElement = getWebElement(xpath, timeout);
            return webElement.getText();
        } catch (Exception ex) {
            throw new Exception("Unable to get text from element '" + getXpathName(xpath) + "' with index " + index
                    + " using xpath [" + xpath + "] in " + timeout + " seconds. Reason is: " + ex.getMessage());
        }
    }

    public String getText(String xpath, int index) throws Exception {
        return getText(xpath, index, short_timeout);
    }

    public String getText(String xpath) throws Exception {
        return getText(xpath, 0, short_timeout);
    }

    /**
     * Get attribute of element
     */
    public String getAttribute(String xpath, int index, String attribute, int timeout) throws Exception {
        try {
            WebElement webElement = getWebElement(xpath, timeout);
            return webElement.getAttribute(attribute);
        } catch (Exception ex) {
            throw new Exception("Unable to get attribute [" + attribute + "] from element '" + getXpathName(xpath)
                    + "' using xpath [" + xpath + "] with index [" + index + "] in " + timeout + " seconds.");
        }
    }

    public String getAttribute(String xpath, int index, String attribute) throws Exception {
        return getAttribute(xpath, index, attribute, short_timeout);
    }

    public String getAttribute(String xpath, String attribute, int timeout) throws Exception {
        return getAttribute(xpath, 0, attribute, timeout);
    }

    /**
     * Return input web element value
     */
    public String getInputValue(String xpath, int index, int timeout) throws Exception {
        try {
            String value = getText(xpath, index, timeout);
            if (value.isEmpty())
                value = getAttribute(xpath, index, "value", timeout);
            return value;
        } catch (Exception ex) {
            throw new Exception("Unable to get text from input '" + getXpathName(xpath)
                    + "' using xpath [" + xpath + "] with index [" + index + "] in " + timeout + " seconds.");
        }
    }

    public String getInputValue(String xpath, int index) throws Exception {
        return getInputValue(xpath, index, short_timeout);
    }

    public String getInputValue(String xpath) throws Exception {
        return getInputValue(xpath, 0, short_timeout);
    }

    /**
     * Clear input element
     */
    public void clearInput(String xpath, int index, int timeout) throws Exception {
        try {
            String chars = getInputValue(xpath, index, timeout);
            String backSpaceKeys = StringUtils.repeat(String.valueOf(Keys.BACK_SPACE), chars.length());
            sendKeys(xpath, index, backSpaceKeys, false, timeout);
        } catch (Exception ex) {
            throw new Exception("Unable to clear input '" + getXpathName(xpath)
                    + "' using xpath [" + xpath + "] with index [" + index + "] in " + timeout + " seconds. Reason: " + ex.getMessage());
        }
    }

    public void clearInput(String xpath, int index) throws Exception {
        clearInput(xpath, index, short_timeout);
    }

    public void clearInput(String xpath) throws Exception {
        clearInput(xpath, 0, short_timeout);
    }

    /**
     * Send keys element
     */
    public synchronized void sendKeys(String xpath, int index, String value, boolean clearBefore, int timeout) throws Exception {
        try {
            if (timeout > 0) {
                waitForVisibility(xpath, index, timeout);
                scrollTo(xpath, index, timeout);
            }
            if (clearBefore)
                clearInput(xpath, index, timeout);
            WebElement we = getWebElements(xpath, timeout).get(index);

            for (CharSequence c : new CharSequence[]{value}) {
                we.sendKeys(c);
                Thread.sleep(50);
            }
        } catch (Exception ex) {
            throw new Exception("Unable to fill input '" + getXpathName(xpath) + "' with index " + index
                    + " using xpath [" + xpath + "] in " + timeout + " seconds. Reason: " + ex.getMessage());
        }
    }

    public void sendKeys(String xpath, int index, String value) throws Exception {
        sendKeys(xpath, index, value, true, short_timeout);
    }

    public void sendKeys(String xpath, String value) throws Exception {
        sendKeys(xpath, 0, value, true, short_timeout);
    }

    /**
     * wait until element is visible
     *
     * @param index - zero-based indexing
     */
    public void waitForVisibility(String xpath, int index, int timeout) {
        waitUntilElementIsVisible(By.xpath(wrapXPathWithIndex(xpath, index)), timeout);
    }

    public void waitForVisibility(String xpath, int index) {
        waitForVisibility(xpath, index, timeout);
    }

    public void waitForVisibility(String xpath) {
        waitForVisibility(xpath, 0, timeout);
    }

    private void waitUntilElementIsVisible(By path, int timeout) {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(path));
    }

    protected void waitUntilElementIsInvisible(By path, int seconds) {
        try {
            setImplicitWait(driver, 0);
            WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(path));
        } finally {
            setImplicitWait(driver, BasePage.timeout);
        }
    }

    /**
     * wait until element is invisible
     */
    public void waitForInvisibility(String xpath, int timeout, int waitForElementTimeout) {
        if (timeout == 0) {
            waitUntilElementIsInvisible(By.xpath(xpath), 1);
        } else {
            try {
                waitForVisibility(xpath, 0, waitForElementTimeout);
            } catch (Exception ignored) {
            }
            waitUntilElementIsInvisible(By.xpath(xpath), timeout);
        }
    }

    public void waitForInvisibility(String xpath, int timeout) {
        waitForInvisibility(xpath, timeout, 1);
    }

    public void waitForInvisibility(String xpath) {
        waitForInvisibility(xpath, short_timeout);
    }

    /**
     * Element centering
     */
    public synchronized void scrollTo(String xpath, int index, int timeout) throws Exception {
        try {
            WebElement we;
            try {
                we = getWebElements(xpath, timeout).get(index);
            } catch (Exception ex) {
                throw new Exception("Unable to find element '" + getXpathName(xpath) + "' with index " + index
                        + " using xpath [" + xpath + "] in " + timeout + " seconds.");
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", we);
            Thread.sleep(250);
        } catch (Exception ex) {
            throw new Exception("Unable to scroll to element '" + getXpathName(xpath)
                    + "' using xpath [" + xpath + "] with index [" + index + "] in " + timeout + " seconds.");
        }
    }

    public void scrollTo(String xpath, int index) throws Exception {
        scrollTo(xpath, index, short_timeout);
    }

    public void scrollTo(String xpath) throws Exception {
        scrollTo(xpath, 0, short_timeout);
    }

    /**
     * Get Web element
     */
    public WebElement getWebElement(String xpath, int timeout) {
        return findElement(By.xpath(xpath), timeout);
    }

    /**
     * Get Web element
     */
    public WebElement getWebElement(String xpath) {
        return getWebElement(xpath, short_timeout);
    }

    /**
     * Get Web elements list
     */
    public List<WebElement> getWebElements(String xpath, int timeout) {
        return findElements(By.xpath(xpath), timeout);
    }

    public List<WebElement> getWebElements(String xpath) {
        return getWebElements(xpath, short_timeout);
    }

    private static void setImplicitWait(WebDriver driver, int seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    private static WebElement findElement(WebDriver driver, By by, int timeout) {
        setImplicitWait(driver, 0);
        WebElement we = null;
        long endTimer = System.currentTimeMillis() + timeout * 1000L;
        while (System.currentTimeMillis() <= endTimer && we == null) {
            try {
                we = driver.findElement(by);
            } catch (Exception e) {
                if (System.currentTimeMillis() > endTimer)
                    throw e;
            }
        }
        setImplicitWait(driver, BasePage.timeout);
        return we;
    }

    private WebElement findElement(By by, int timeout) {
        return findElement(this.driver, by, timeout);
    }

    private WebElement findElement(By by) {
        return findElement(by, short_timeout);
    }

    private static List<WebElement> findElements(WebDriver driver, By by, int timeout) {
        setImplicitWait(driver, 0);
        List<WebElement> we = new ArrayList<>();
        long endTimer = System.currentTimeMillis() + (timeout * 1000L);
        do {
            try {
                we = driver.findElements(by);
            } catch (Exception e) {
                if (System.currentTimeMillis() > endTimer)
                    throw e;
            }
        } while ((System.currentTimeMillis() <= endTimer) && (we.size() == 0));
        setImplicitWait(driver, BasePage.timeout);
        return we;
    }

    private List<WebElement> findElements(By by, int timeout) {
        return findElements(this.driver, by, timeout);
    }

    private List<WebElement> findElements(By by) {
        return findElements(by, short_timeout);
    }

    /**
     * Click on web element
     */
    public void click(String xpath, int index, int timeout) throws Exception {
        try {
            WebElement webElement = getWebElement(xpath, timeout);
            scrollTo(xpath, index, timeout);
            webElement.click();
        } catch (Exception ex) {
            throw new Exception("Unable to click on element '" + getXpathName(xpath)
                    + "' using xpath [" + xpath + "] with index [" + index + "] in " + timeout + " seconds.");
        }
    }

    public void click(String xpath, int index) throws Exception {
        click(xpath, index, short_timeout);
    }

    public void click(String xpath) throws Exception {
        click(xpath, 0, short_timeout);
    }
}
