package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
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

    private static final String CREATE_NEW_PROJECT_BUTTON = "//button/span/span[contains(text(), 'Create new project')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    protected void pageLoading() {
        try {
            log.info("!!!DEBUG LOG. TRY TO CHECK IF PAGE LOADED");
            if (!(isVisible(CREATE_NEW_PROJECT_BUTTON, 0, long_timeout))) throw new Exception();
        } catch (Exception ex) {
            throw new AssertionError("Login Page was not loaded");
        }
        log.info("!!!DEBUG LOG. OK! Page 'ProjectsPage' is loaded");
    }

    public boolean isPageLoaded() {
        log.info("!!!LOG FOR CATCH A BUG. Trying to check if 'CREATE_NEW_PROJECT_BUTTON' is Visible");
        return isVisible(CREATE_NEW_PROJECT_BUTTON);
    }
}
