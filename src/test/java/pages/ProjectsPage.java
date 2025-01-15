package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.base.BasePage;

@Log4j2
public class ProjectsPage extends BasePage<ProjectsPage> {

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
    private static final String SPECIFIC_PROJECT_ACTION_MENU = PROJECTS_LIST_WRAPPER + "//a[contains(text(),%s)]/ancestor::tr" +
            "//button[@aria-label='Open action menu']/span";
    private static final String ACTION_MENU_WRAPPER = "//div[contains(@data-trigger, 'MenuTrigger')]//div[@role='menu']";
    private static final String SETTINGS_BUTTON = ACTION_MENU_WRAPPER + "//div[@data-testid='settings']";
    private static final String REMOVE_BUTTON = ACTION_MENU_WRAPPER + "//div[@data-testid='remove']";

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

    public boolean isProjectWithSpecificNameVisible(String projectName) {
        return isVisible(String.format(SPECIFIC_PROJECT_NAME_IN_PROJECTS_LIST, Quotes.escape(projectName)));
    }

    public boolean isProjectWithSpecificNameInvisible(String projectName) {
        return isInvisible(String.format(SPECIFIC_PROJECT_NAME_IN_PROJECTS_LIST, Quotes.escape(projectName)));
    }

    public ProjectSettingsPage clickSettingsOnActionMenuForSpecificProject(String projectName) {
        log.info("Click 'Settings' on action menu for '{}' project", projectName);
        clickOnSpecificActionMenu(projectName);
        clickSettingsOnActionMenu();
        return new ProjectSettingsPage(driver);
    }

    public DeleteProjectPage clickRemoveOnActionMenuForSpecificProject(String projectName) {
        log.info("Click 'Remove' on action menu for '{}' project", projectName);
        clickOnSpecificActionMenu(projectName);
        clickRemoveOnActionMenu();
        return new DeleteProjectPage(driver);
    }

    public ProjectPage clickOnSpecificProject(String projectName) {
        click(String.format(SPECIFIC_PROJECT_NAME_IN_PROJECTS_LIST, Quotes.escape(projectName)));
        waitForInvisibility(CREATE_NEW_PROJECT_BUTTON);
        return new ProjectPage(driver);
    }

    private void clickOnSpecificActionMenu(String projectName) {
        click(String.format(SPECIFIC_PROJECT_ACTION_MENU, Quotes.escape(projectName)));
        waitForVisibility(ACTION_MENU_WRAPPER);
    }

    private void clickSettingsOnActionMenu() {
        click(SETTINGS_BUTTON);
        waitForInvisibility(ACTION_MENU_WRAPPER);
    }

    private void clickRemoveOnActionMenu() {
        click(REMOVE_BUTTON);
        waitForInvisibility(ACTION_MENU_WRAPPER);
    }

    public boolean isPageLoaded() {
        return isVisible(CREATE_NEW_PROJECT_BUTTON);
    }
}
