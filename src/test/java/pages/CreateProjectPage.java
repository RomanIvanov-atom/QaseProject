package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;
import wrappers.Input;
import wrappers.RadioButton;

@Log4j2
public class CreateProjectPage extends BasePage {

    public CreateProjectPage(WebDriver driver) {
        super(driver);
        waitForDOMLoaded();
        pageLoading();
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String CREATE_PROJECT_MODAL_WRAPPER =
            "//div[@id='modals']//h3[contains(text(),'Create new project')]//ancestor::header/..";
    private static final String CREATE_PROJECT_BUTTON = CREATE_PROJECT_MODAL_WRAPPER + "//button[@type='submit']";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    @Override
    protected void pageLoading() {
        log.info("Loading Create Project modal");
        try {
            if (!(isVisible(CREATE_PROJECT_BUTTON, 0, timeout))) throw new Exception();
        } catch (Exception ex) {
            throw new AssertionError("Projects modal was not loaded");
        }
    }

    public ProjectPage createNewProject(String projectName, String projectCode) {
        log.info("Creating new project with '{}' name and code '{}'", projectName, projectCode);
        new Input(driver, "Project name").write(projectName);
        new Input(driver, "Project code").write(projectCode);
        new RadioButton(driver, "Project access type").select("Public");
        clickCreateProjectButton();
        return new ProjectPage(driver);
    }

    public void clickCreateProjectButton() {
        log.info("Click 'Create project' button");
        click(CREATE_PROJECT_BUTTON);
    }
}
