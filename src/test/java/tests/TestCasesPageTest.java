package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProjectPage;
import pages.ProjectsPage;
import tests.base.BaseTest;
import utils.constants.Constants;

import static api.adapters.ProjectAPI.createProject;
import static api.adapters.ProjectAPI.deleteProject;
import static dto.testCase.TestCaseFactory.getFilledAccount;
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
    public void testCreateTestCase() {
        createProject(projectName, projectCode);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.reloadPage()
                .clickOnSpecificProject(projectName)
                .clickCreateTestCase()
                .createTestCase(getFilledAccount(testCaseTitle, testCaseDescription, testCasePreConditions, testCasePostConditions));
        assertTrue(new ProjectPage(driver).isSpecificTestCaseVisible(testCaseTitle), "Created test case wasn't found in test cases list");

        deleteProject(projectCode);
    }

//    @Test(testName = "#9 Test update test case", description = "#9 Test update test case")
//    @Description("#9 Test update test case")
//    public void testUpdateTestCase() {
//        final String projectNameUpdated = projectName + generateRandomAlphaNumericUpperCaseString(5);
//        final String projectCodeUpdated = projectCode + generateRandomAlphaNumericUpperCaseString(4);
//
//        createProject(projectName, projectCode);
//
//        ProjectsPage projectsPage = new ProjectsPage(driver);
//        projectsPage.reloadPage();
//        projectsPage
//                .clickSettingsOnActionMenuForSpecificProject(projectName)
//                .updateProject(projectNameUpdated, projectCodeUpdated)
//                .clickProjectsButtonOnHeader();
//        assertTrue(projectsPage.isProjectWithSpecificNameVisible(projectNameUpdated), "Updated project wasn't found in projects list");
//
//        deleteProject(projectCodeUpdated);
//    }
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
