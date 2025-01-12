package pages;

import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        waitForDOMLoaded();
        pageLoading();
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String LOGIN_BUTTON = "//button[@type='submit']";
    private static final String USERNAME_INPUT = "//input[@name='email']";
    private static final String PASSWORD_INPUT = "//input[@name='password']";
    private static final String ERROR_MESSAGE = "//div[@role='alert']//span[contains(text(), 'These credentials do not match our records.')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    protected void pageLoading() {
        try {
            if (!(isVisible(LOGIN_BUTTON, 0, long_timeout))) throw new Exception();
        } catch (Exception ex) {
            throw new AssertionError("Login Page was not loaded");
        }
    }

    public boolean isPageLoaded() {
        return isVisible(LOGIN_BUTTON);
    }

    public LoginPage fillUserName(String value) {
        sendKeys(USERNAME_INPUT, value);
        return this;
    }

    public LoginPage fillPassword(String value) {
        sendKeys(PASSWORD_INPUT, value);
        return this;
    }

    public void clickLoginButton() {
        click(LOGIN_BUTTON);
    }

    public void doLogin(String login, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillUserName(login)
                .fillPassword(password);
        loginPage.clickLoginButton();
        waitForInvisibility(LOGIN_BUTTON);
    }

    public boolean isInputErrorVisible() {
        return isVisible(ERROR_MESSAGE);
    }
}
