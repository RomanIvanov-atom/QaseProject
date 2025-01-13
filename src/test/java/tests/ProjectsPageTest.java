package tests;

import api.adapters.ProjectAPI;
import api.models.CreateProjectRq;
import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProjectsPage;
import tests.base.BaseTest;
import utils.constants.Constants;

import static org.testng.Assert.*;
import static utils.DataGenerator.generateRandomAlphaNumericString;

@Log4j2
public class ProjectsPageTest extends BaseTest {

    @BeforeMethod
    @Step("Open Projects Page")
    public void openPage() {
        driver.get(Constants.PROJECTS_BASE_URL);
    }

    @Test(testName = "#5 Test create project", description = "#5 Test create project")
    @Description("#5 Test create project")
    public void createProjectTest() {
        final String projectName = generateRandomAlphaNumericString(10);
        final String projectCode = generateRandomAlphaNumericString(4);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage
                .clickCreateProjectButton()
                .createNewProject(projectName, projectCode)
                .clickProjectsButtonOnHeader();
        assertTrue(projectsPage.isProjectWithSpecificNameVisible(projectName), "Created project wasn't found in projects list");

        new ProjectAPI().deleteProject(projectCode);
    }

    @Test(testName = "#6 Test update project", description = "#6 Test update project")
    @Description("#6 Test update project")
    public void updateProjectTest() {
        final String projectName = generateRandomAlphaNumericString(10);
        final String projectCode = generateRandomAlphaNumericString(4);
        final String projectNameUpdated = projectName + generateRandomAlphaNumericString(5);
        final String projectCodeUpdated = projectCode + generateRandomAlphaNumericString(4);

        CreateProjectRq rq = CreateProjectRq.builder()
                .title(projectName)
                .code(projectCode)
                .build();
        new ProjectAPI().createProject(rq);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.reloadPage();
        projectsPage
                .clickSettingsOnActionMenuForSpecificProject(projectName)
                .updateProject(projectNameUpdated, projectCodeUpdated)
                .clickProjectsButtonOnHeader();
        assertTrue(projectsPage.isProjectWithSpecificNameVisible(projectNameUpdated), "Updated project wasn't found in projects list");

        new ProjectAPI().deleteProject(projectCodeUpdated);
    }

    @Test(testName = "#7 Test delete project", description = "#7 Test delete project")
    @Description("#7 Test delete project")
    public void deleteProjectTest() {
        final String projectName = generateRandomAlphaNumericString(10);
        final String projectCode = generateRandomAlphaNumericString(4);

        CreateProjectRq rq = CreateProjectRq.builder()
                .title(projectName)
                .code(projectCode)
                .build();
        new ProjectAPI().createProject(rq);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.reloadPage();
        projectsPage
                .clickRemoveOnActionMenuForSpecificProject(projectName)
                .clickConfirmDeleteProjectButton();
        assertTrue(projectsPage.isProjectWithSpecificNameInvisible(projectName), "Deleted project was found in projects list");
    }
}