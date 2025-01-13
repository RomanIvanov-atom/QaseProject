package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProjectsPage;
import tests.base.BaseTest;
import utils.DataGenerator;
import utils.constants.Constants;

import static org.testng.Assert.assertTrue;

public class ProjectsTest extends BaseTest {

    @BeforeMethod
    public void openPage() {
        driver.get(Constants.PROJECTS_BASE_URL);
    }

    @Test
    public void createProjectTest() {
        final String projectName = DataGenerator.generateRandomAlphaNumericString(10);
        final String projectCode = DataGenerator.generateRandomAlphaNumericString(4);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage
                .clickCreateProjectButton()
                .createNewProject(projectName, projectCode)
                .clickProjectsButtonOnHeader();
        assertTrue(projectsPage.isCreatedProjectVisible(projectName), "Created project wasn't found in projects list");
    }
}
