package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.ProjectPage;
import pages.base.BaseElement;

@Log4j2
public class DeleteTestCaseModal extends BaseElement {
    public DeleteTestCaseModal(WebDriver driver) {
        super(driver);
        log.info("Opening delete test case modal");
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String DELETE_TEST_CASE_MODAL_WRAPPER = "//h2[text()='Delete test case']/ancestor::div[@id='modals']//dialog";
    private static final String CONFIRM_DELETE_BUTTON = DELETE_TEST_CASE_MODAL_WRAPPER + "//span[contains(text(),'Delete')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    public ProjectPage clickConfirmDeleteTestCaseButton() {
        log.info("Click 'Delete project' button");
        click(CONFIRM_DELETE_BUTTON);
        waitForInvisibility(DELETE_TEST_CASE_MODAL_WRAPPER);
        return new ProjectPage(driver);
    }
}
