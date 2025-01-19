package tests;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProjectPage;
import pages.ProjectsPage;
import pages.components.EditSuiteModal;
import tests.base.BaseTest;
import utils.constants.Constants;

import static api.adapters.ProjectAPI.createProject;
import static api.adapters.ProjectAPI.deleteProject;
import static api.adapters.SuiteAPI.createSuite;
import static org.testng.Assert.assertTrue;
import static utils.DataGenerator.generateRandomAlphaNumericUpperCaseString;

@Log4j2
public class SuitesPageTest extends BaseTest {

    private final String projectName = generateRandomAlphaNumericUpperCaseString(10);
    private final String projectCode = generateRandomAlphaNumericUpperCaseString(4);
    private final String suiteTitle = generateRandomAlphaNumericUpperCaseString(4) + generateRandomAlphaNumericUpperCaseString(4);
    private final String suiteDescription = generateRandomAlphaNumericUpperCaseString(5);
    private final String suitePreConditions = generateRandomAlphaNumericUpperCaseString(7);

    @BeforeMethod
    @Step("Open Projects Page")
    public void openPage() {
        driver.get(Constants.PROJECTS_BASE_URL);
    }

    @Test(testName = "#11 Test create suite", description = "#11 Test create suite")
    @Description("#11 Test create suite")
    @Feature("Suite")
    @TmsLink("https://some-tms.com/test/11")
    @Owner("Roman R")
    @Flaky
    public void testCreateSuite() {
        createProject(projectName, projectCode);

        ProjectPage projectPage = new ProjectsPage(driver)
                .reloadPage()
                .clickOnSpecificProject(projectName)
                .clickCreateSuiteButton()
                .createNewSuite(suiteTitle, suiteDescription, suitePreConditions);
        assertTrue(projectPage.isSpecificSuiteVisible(suiteTitle), "Created suite wasn't found in suites list");

        deleteProject(projectCode);
    }

    @Test(testName = "#12 Test update suite", description = "#12 Test update suite")
    @Description("#12 Test update suite")
    @Feature("Suite")
    @TmsLink("https://some-tms.com/test/12")
    @Owner("Roman R")
    @Flaky
    public void testUpdateSuite() {
        final String suiteTitleUpdated = suiteTitle + generateRandomAlphaNumericUpperCaseString(2);
        final String suiteDescriptionUpdated = suiteDescription + generateRandomAlphaNumericUpperCaseString(3);
        final String suitePreConditionsUpdated = suitePreConditions + generateRandomAlphaNumericUpperCaseString(2);

        createProject(projectName, projectCode);
        createSuite(projectCode, suiteTitle, suiteDescription, suitePreConditions);

        EditSuiteModal editSuiteModal  = new ProjectsPage(driver)
                .reloadPage()
                .clickOnSpecificProject(projectName)
                .clickEditSpecificSuiteButton(suiteTitle)
                .editSuite(suiteTitleUpdated, suiteDescriptionUpdated, suitePreConditionsUpdated)
                .clickEditSpecificSuiteButton(suiteTitleUpdated);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(editSuiteModal.getTextFromSpecificInputField("Suite name"), suiteTitleUpdated,
                "Suite name wasn't changed");
        softAssert.assertEquals(editSuiteModal.getTextFromSpecificInputField("Description"), suiteDescriptionUpdated,
                "Description wasn't changed");
        softAssert.assertEquals(editSuiteModal.getTextFromSpecificInputField("Preconditions"), suitePreConditionsUpdated,
                "Preconditions wasn't changed");
        softAssert.assertAll();

        deleteProject(projectCode);
    }

    @Test(testName = "#13 Test delete suite", description = "#13 Test delete suite")
    @Description("#13 Test delete suite")
    @Feature("Suite")
    @TmsLink("https://some-tms.com/test/13")
    @Owner("Roman R")
    public void testDeleteSuite() {
        createProject(projectName, projectCode);
        createSuite(projectCode, suiteTitle, suiteDescription, suitePreConditions);

        ProjectPage projectPage  = new ProjectsPage(driver)
                .reloadPage()
                .clickOnSpecificProject(projectName)
                .clickDeleteSpecificSuiteButton(suiteTitle)
                .clickDeleteSuiteButton();
        assertTrue(projectPage.isSpecificSuiteInvisible(suiteTitle), "Deleted suite was found in suite list");

        deleteProject(projectCode);
    }
}
