package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

@Log4j2
public class DeleteProjectPage extends BasePage {
    public DeleteProjectPage(WebDriver driver) {
        super(driver);
        pageLoading();
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String DELETE_PROJECT_MODAL_WRAPPER = "//h2[text()='Delete project']/ancestor::div[@id='modals']//dialog";
    private static final String CONFIRM_DELETE_BUTTON = DELETE_PROJECT_MODAL_WRAPPER + "//span[contains(text(),'Delete project')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    @Override
    protected void pageLoading() {
        log.info("Loading Delete project modal");
        try {
            if (!(isVisible(CONFIRM_DELETE_BUTTON, 0, timeout))) throw new Exception();
        } catch (Exception ex) {
            throw new AssertionError("Delete project modal was not loaded");
        }
    }

    public ProjectsPage clickConfirmDeleteProjectButton() {
        log.info("Click 'Delete project' button");
        click(CONFIRM_DELETE_BUTTON);
        waitForInvisibility(DELETE_PROJECT_MODAL_WRAPPER);
        return new ProjectsPage(driver);
    }
}
