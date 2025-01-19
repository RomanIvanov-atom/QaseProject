package tests;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProjectsPage;
import tests.base.BaseTest;
import tests.base.annotations.Login;
import utils.constants.Constants;

import static org.testng.Assert.assertTrue;
import static utils.DataGenerator.generateRandomAlphaNumericUpperCaseString;

@Log4j2
public class LoginPageTest extends BaseTest {

    private final String incorrectUsername = generateRandomAlphaNumericUpperCaseString(6) + "@gmail.com";
    private final String incorrectPassword = generateRandomAlphaNumericUpperCaseString(10);

    @BeforeMethod
    @Step("Open Login Page")
    public void openPage() {
        driver.get(Constants.LOGIN_BASE_URL);
    }

    @Test(testName = "#1 Test Login with a valid username and password", description = "#1 Test Login with a valid username and password")
    @Description("#1 Test Login with a valid username and password")
    @Epic("EPIC-001 First quart")
    @Story("ST-01 First story")
    @Feature("Login")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("https://some-tms.com/test/1")
    @Owner("Roman R")
    @Login(doLogIn = false)
    public void testLoginWithAValidUsernameAndPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .fillUserName(Constants.USERNAME)
                .fillPassword(Constants.PASSWORD)
                .clickLoginButton();
        assertTrue(new ProjectsPage(driver).isPageLoaded(), "Project Page was not loaded");
    }

    @Test(testName = "#2 Test Login with a valid username and invalid password",
            description = "#2 Test Login with a valid username and invalid password")
    @Description("#2 Test Login with a valid username and invalid password")
    @Feature("Login")
    @TmsLink("https://some-tms.com/test/2")
    @Owner("Roman R")
    @Login(doLogIn = false)
    public void testLoginWithAValidUsernameAndInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .fillUserName(Constants.USERNAME)
                .fillPassword(incorrectPassword)
                .clickLoginButton();
        assertTrue(loginPage.isInputErrorVisible(), "Message about wrong entered credentials isn't shown");
    }

    @Test(testName = "#3 Test Login with invalid username and valid password",
            description = "#3 Test Login with invalid username and valid password")
    @Description("#3 Test Login with invalid username and valid password")
    @Feature("Login")
    @TmsLink("https://some-tms.com/test/3")
    @Owner("Roman R")
    @Login(doLogIn = false)
    public void testLoginWithInvalidUsernameAndValidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .fillUserName(incorrectUsername)
                .fillPassword(Constants.PASSWORD)
                .clickLoginButton();
        assertTrue(loginPage.isInputErrorVisible(), "Message about wrong entered credentials isn't shown");
    }

    @Test(testName = "#4 Test Login with invalid username and invalid password",
            description = "#4 Test Login with invalid username and invalid password")
    @Description("#4 Test Login with invalid username and invalid password")
    @Feature("Login")
    @TmsLink("https://some-tms.com/test/4")
    @Owner("Roman R")
    @Login(doLogIn = false)
    public void testLoginWithInvalidUsernameAndInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .fillUserName(incorrectUsername)
                .fillPassword(incorrectPassword)
                .clickLoginButton();
        assertTrue(loginPage.isInputErrorVisible(), "Message about wrong entered credentials isn't shown");
    }
}
