package tests;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProjectPage;
import pages.ProjectsPage;
import pages.TestCasePage;
import tests.base.BaseTest;
import utils.constants.Constants;

import static api.adapters.ProjectAPI.createProject;
import static api.adapters.ProjectAPI.deleteProject;
import static api.adapters.TestCaseAPI.createTestCase;
import static dto.testCase.TestCaseFactory.getFilledAccount;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.DataGenerator.generateRandomAlphaNumericUpperCaseString;

@Log4j2
public class TestCasesPageTest extends BaseTest {

    private final String projectName = generateRandomAlphaNumericUpperCaseString(10);
    private final String projectCode = generateRandomAlphaNumericUpperCaseString(4);
    private final String testCaseTitle = generateRandomAlphaNumericUpperCaseString(4) + generateRandomAlphaNumericUpperCaseString(4);;
    private final String testCaseDescription = generateRandomAlphaNumericUpperCaseString(20);
    private final String testCasePreConditions = generateRandomAlphaNumericUpperCaseString(18);
    private final String testCasePostConditions = generateRandomAlphaNumericUpperCaseString(19);

    @BeforeMethod
    @Step("Open Projects Page")
    public void openPage() {
        driver.get(Constants.PROJECTS_BASE_URL);
    }

    @Test(testName = "#8 Test create test case", description = "#8 Test create test case")
    @Description("#8 Test create test case")
    @Feature("Test-case")
    @TmsLink("https://some-tms.com/test/8")
    @Owner("Roman R")
    public void testCreateTestCase() {
        createProject(projectName, projectCode);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.reloadPage()
                .clickOnSpecificProject(projectName)
                .clickCreateTestCase()
                .fillAllTestCaseFields(getFilledAccount(testCaseTitle, testCaseDescription, testCasePreConditions, testCasePostConditions))
                .clickSave();
        assertTrue(new ProjectPage(driver).isSpecificTestCaseVisible(testCaseTitle), "Created test case wasn't found in test cases list");

        deleteProject(projectCode);
    }

    @Test(testName = "#9 Test update test case", description = "#9 Test update test case")
    @Description("#9 Test update test case")
    @Feature("Test-case")
    @TmsLink("https://some-tms.com/test/9")
    @Owner("Roman R")
    public void testUpdateTestCase() {
        final String testCaseTitleUpdated = testCaseTitle + generateRandomAlphaNumericUpperCaseString(2);
        final String testCaseDescriptionUpdated = testCaseDescription + generateRandomAlphaNumericUpperCaseString(3);
        final String testCasePreConditionsUpdated = testCaseDescription + generateRandomAlphaNumericUpperCaseString(2);
        final String testCasePostConditionsUpdated = testCaseDescription + generateRandomAlphaNumericUpperCaseString(4);

        createProject(projectName, projectCode);
        createTestCase(projectCode, testCaseTitle, testCaseDescription, testCasePreConditions, testCasePostConditions);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.reloadPage()
                .clickOnSpecificProject(projectName)
                .clickSpecificTestCase(testCaseTitle)
                .clickEditTestCaseButton()
                .fillBasicTestCaseFields(getFilledAccount(testCaseTitleUpdated, testCaseDescriptionUpdated, testCasePreConditionsUpdated, testCasePostConditionsUpdated))
                .clickSave()
                .clickSpecificTestCase(testCaseTitleUpdated)
                .clickEditTestCaseButton();

        SoftAssert softAssert = new SoftAssert();
        TestCasePage testCasePage = new TestCasePage(driver);
        assertEquals(testCasePage.getTextFromSpecificInputField("Title"), testCaseTitleUpdated,
                "Title wasn't changed");
        assertEquals(testCasePage.getTextFromSpecificTextAreaField("Description"), testCaseDescriptionUpdated,
                "Description wasn't changed");
        assertEquals(testCasePage.getTextFromSpecificTextAreaField("Pre-conditions"), testCasePreConditionsUpdated,
                "Pre-conditions wasn't changed");
        assertEquals(testCasePage.getTextFromSpecificTextAreaField("Post-conditions"), testCasePostConditionsUpdated,
                "Post-conditions wasn't changed");
        softAssert.assertAll();

        deleteProject(projectCode);
    }
//
//    @Test(testName = "#10 Test delete test case", description = "#10 Test delete test case")
//    @Description("#10 Test delete test case")
//    public void testDeleteTestCase() {
//        createProject(projectName, projectCode);
//
//        ProjectsPage projectsPage = new ProjectsPage(driver);
//        projectsPage.reloadPage();
//        projectsPage
//                .clickRemoveOnActionMenuForSpecificProject(projectName)
//                .clickConfirmDeleteProjectButton();
//        assertTrue(projectsPage.isProjectWithSpecificNameInvisible(projectName), "Deleted project was found in projects list");
//    }
}
