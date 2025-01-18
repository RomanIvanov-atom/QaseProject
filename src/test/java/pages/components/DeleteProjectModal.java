package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.ProjectsPage;
import pages.base.BaseElement;

@Log4j2
public class DeleteProjectModal extends BaseElement {
    public DeleteProjectModal(WebDriver driver) {
        super(driver);
        log.info("Opening delete project modal");
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String DELETE_PROJECT_MODAL_WRAPPER = "//h2[text()='Delete project']/ancestor::div[@id='modals']//dialog";
    private static final String CONFIRM_DELETE_BUTTON = DELETE_PROJECT_MODAL_WRAPPER + "//span[contains(text(),'Delete project')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    public ProjectsPage clickConfirmDeleteProjectButton() {
        log.info("Click 'Delete project' button");
        click(CONFIRM_DELETE_BUTTON);
        waitForInvisibility(DELETE_PROJECT_MODAL_WRAPPER);
        return new ProjectsPage(driver);
    }
}
