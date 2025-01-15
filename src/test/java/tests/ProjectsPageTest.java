package tests;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProjectsPage;
import tests.base.BaseTest;
import utils.constants.Constants;

import static api.adapters.ProjectAPI.createProject;
import static api.adapters.ProjectAPI.deleteProject;
import static org.testng.Assert.assertTrue;
import static utils.DataGenerator.generateRandomAlphaNumericUpperCaseString;

@Log4j2
public class ProjectsPageTest extends BaseTest {

    private final String projectName = generateRandomAlphaNumericUpperCaseString(10);
    private final String projectCode = generateRandomAlphaNumericUpperCaseString(4);

    @BeforeMethod
    @Step("Open Projects Page")
    public void openPage() {
        driver.get(Constants.PROJECTS_BASE_URL);
    }

    @Test(testName = "#5 Test create project", description = "#5 Test create project")
    @Description("#5 Test create project")
    @Feature("Projects")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("https://some-tms.com/test/5")
    @Owner("Roman R")
    public void testCreateProject() {
        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage
                .clickCreateProjectButton()
                .createNewProject(projectName, projectCode)
                .clickProjectsButtonOnHeader();
        assertTrue(projectsPage.isProjectWithSpecificNameVisible(projectName), "Created project wasn't found in projects list");

        deleteProject(projectCode);
    }

    @Test(testName = "#6 Test update project", description = "#6 Test update project")
    @Description("#6 Test update project")
    @Feature("Projects")
    @TmsLink("https://some-tms.com/test/6")
    @Owner("Roman R")
    public void testUpdateProject() {
        final String projectNameUpdated = projectName + generateRandomAlphaNumericUpperCaseString(5);
        final String projectCodeUpdated = projectCode + generateRandomAlphaNumericUpperCaseString(4);

        createProject(projectName, projectCode);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.reloadPage()
                .clickSettingsOnActionMenuForSpecificProject(projectName)
                .updateProject(projectNameUpdated, projectCodeUpdated)
                .clickProjectsButtonOnHeader();
        assertTrue(projectsPage.isProjectWithSpecificNameVisible(projectNameUpdated), "Updated project wasn't found in projects list");

        deleteProject(projectCodeUpdated);
    }

    @Test(testName = "#7 Test delete project", description = "#7 Test delete project")
    @Description("#7 Test delete project")
    @Feature("Projects")
    @TmsLink("https://some-tms.com/test/7")
    @Owner("Roman R")
    public void testDeleteProject() {
        createProject(projectName, projectCode);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.reloadPage()
                .clickRemoveOnActionMenuForSpecificProject(projectName)
                .clickConfirmDeleteProjectButton();
        assertTrue(projectsPage.isProjectWithSpecificNameInvisible(projectName), "Deleted project was found in projects list");
    }
}
