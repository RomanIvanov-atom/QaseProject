package tests;

import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProjectsPage;
import tests.base.BaseTest;
import utils.constants.Constants;

@Log4j2
public class LoginPageTest extends BaseTest {

    @BeforeMethod
    public void openPage() {
        driver.get(Constants.LOGIN_BASE_URL);
    }

    @Test
    public void testLoginWithAValidUsernameAndPassword() throws Exception {
        log.info("Precondition. Navigate to login page as a non-logged user");
        LoginPage loginPage = new LoginPage(driver);

        log.info("Step 1. Fill the User Field and the Password field with valid data then click on 'Login' button");
        loginPage
                .fillUserName(Constants.USERNAME)
                .fillPassword(Constants.PASSWORD)
                .clickLoginButton();

        log.info("Attempt to confirm User logs in and is directed to the Projects Page");
        ProjectsPage projectsPage = new ProjectsPage(driver);
        Assert.assertTrue(projectsPage.isPageLoaded());
    }
}
