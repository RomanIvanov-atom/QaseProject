package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.ProjectPage;
import pages.base.BaseElement;

@Log4j2
public class DeleteSuiteModal extends BaseElement {
    public DeleteSuiteModal(WebDriver driver) {
        super(driver);
        log.info("Opening delete suite modal");
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String DELETE_SUITE_MODAL_WRAPPER = "//h2[text()='Delete Suite']/ancestor::div[@id='modals']//dialog";
    private static final String DELETE_BUTTON = DELETE_SUITE_MODAL_WRAPPER + "//span[contains(text(),'Delete')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    public ProjectPage clickDeleteSuiteButton() {
        log.info("Click 'Delete' button");
        click(DELETE_BUTTON);
        waitForInvisibility(DELETE_SUITE_MODAL_WRAPPER);
        return new ProjectPage(driver);
    }
}
