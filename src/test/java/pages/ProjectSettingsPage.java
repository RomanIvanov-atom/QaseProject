package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;
import wrappers.Input;

@Log4j2
public class ProjectSettingsPage extends BasePage {
    public ProjectSettingsPage(WebDriver driver) {
        super(driver);
        waitForDOMLoaded();
        pageLoading();
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String UPDATE_SETTINGS_BUTTON = "//button[@type='submit']";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    @Override
    protected void pageLoading() {
        log.info("Loading Project settings page");
        try {
            if (!(isVisible(UPDATE_SETTINGS_BUTTON, 0, timeout))) throw new Exception();
        } catch (Exception ex) {
            throw new AssertionError("Project settings Page was not loaded");
        }
    }

    public ProjectPage updateProject(String projectName, String projectCode) {
        new Input(driver, "Project name").write(projectName);
        new Input(driver, "Project code").write(projectCode);
        clickUpdateSettingsButton();
        return new ProjectPage(driver);
    }

    public void clickUpdateSettingsButton() {
        log.info("Click 'Update settings' button");
        click(UPDATE_SETTINGS_BUTTON);
    }
}
