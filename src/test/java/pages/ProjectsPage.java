package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.base.BasePage;

@Log4j2
public class ProjectsPage extends BasePage {

    public ProjectsPage(WebDriver driver) {
        super(driver);
        waitForDOMLoaded();
        pageLoading();
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String PROJECT_LIST_CONTROLS = "//div[@aria-label='Project list controls']";
    private static final String CREATE_NEW_PROJECT_BUTTON = PROJECT_LIST_CONTROLS + "//button//span[contains(text(), 'Create new project')]";
    private static final String PROJECTS_LIST_WRAPPER = "//div[@id='application-content']//table";
    private static final String SPECIFIC_PROJECT_NAME_IN_PROJECTS_LIST = PROJECTS_LIST_WRAPPER + "//a[contains(text(),%s)]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    public void pageLoading() {
        log.info("Loading Projects Page");
        try {
            if (!(isVisible(CREATE_NEW_PROJECT_BUTTON, 0, timeout))) throw new Exception();
        } catch (Exception ex) {
            throw new AssertionError("Projects Page was not loaded");
        }
    }

    public CreateProjectPage clickCreateProjectButton() {
        log.info("Click 'Create new project' button");
        click(CREATE_NEW_PROJECT_BUTTON);
        return new CreateProjectPage(driver);
    }

    public boolean isCreatedProjectVisible(String projectName) {
        return isVisible(String.format(SPECIFIC_PROJECT_NAME_IN_PROJECTS_LIST, Quotes.escape(projectName)));
    }

    public boolean isPageLoaded() {
        return isVisible(CREATE_NEW_PROJECT_BUTTON);
    }
}
