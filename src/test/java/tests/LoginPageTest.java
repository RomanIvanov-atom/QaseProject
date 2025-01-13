package tests;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProjectsPage;
import tests.base.BaseTest;
import tests.base.annotations.Login;
import utils.DataGenerator;
import utils.constants.Constants;

import static org.testng.Assert.assertTrue;

@Log4j2
public class LoginPageTest extends BaseTest {

    @BeforeMethod
    public void openPage() {
        driver.get(Constants.LOGIN_BASE_URL);
    }

    @Test
    @Login(doLogIn = false)
    public void testLoginWithAValidUsernameAndPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .fillUserName(Constants.USERNAME)
                .fillPassword(Constants.PASSWORD)
                .clickLoginButton();
        ProjectsPage projectsPage = new ProjectsPage(driver);
        assertTrue(projectsPage.isPageLoaded(), "Project Page was not loaded");
    }

    @Test
    @Login(doLogIn = false)
    public void testLoginWithAValidUsernameAndInvalidPassword() {
        String incorrectPassword = DataGenerator.generateRandomAlphaNumericString(10);

        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .fillUserName(Constants.USERNAME)
                .fillPassword(incorrectPassword)
                .clickLoginButton();
        assertTrue(loginPage.isInputErrorVisible(), "Message about wrong entered credentials isn't shown");
    }

    @Test
    @Login(doLogIn = false)
    public void testLoginWithInvalidUsernameAndValidPassword() {
        String incorrectUsername = DataGenerator.generateRandomAlphaNumericString(6) + "@gmail.com";

        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .fillUserName(incorrectUsername)
                .fillPassword(Constants.PASSWORD)
                .clickLoginButton();
        assertTrue(loginPage.isInputErrorVisible(), "Message about wrong entered credentials isn't shown");
    }

    @Test
    @Login(doLogIn = false)
    public void testLoginWithInvalidUsernameAndInvalidPassword() {
        String incorrectPassword = DataGenerator.generateRandomAlphaNumericString(10);
        String incorrectUsername = DataGenerator.generateRandomAlphaNumericString(6) + "@gmail.com";

        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .fillUserName(incorrectUsername)
                .fillPassword(incorrectPassword)
                .clickLoginButton();
        assertTrue(loginPage.isInputErrorVisible(), "Message about wrong entered credentials isn't shown");
    }
}
