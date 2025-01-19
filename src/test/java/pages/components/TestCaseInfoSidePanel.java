package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.TestCasePage;
import pages.base.BaseElement;

@Log4j2
public class TestCaseInfoSidePanel extends BaseElement {

    public TestCaseInfoSidePanel(WebDriver driver) {
        super(driver);
        log.info("Opening test case info side panel");
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String PANEL_WRAPPER = "//*[@id='suitecases-container']/div[2]";
    private static final String EDIT_TEST_CASE_BUTTON = PANEL_WRAPPER + "//span[contains(text(),'Edit')]";
    private static final String DELETE_TEST_CASE_BUTTON = PANEL_WRAPPER + "//span[contains(text(),'Delete')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    public TestCasePage clickEditTestCaseButton() {
        log.info("Click 'Edit' test case button");
        click(EDIT_TEST_CASE_BUTTON);
        waitForInvisibility(PANEL_WRAPPER);
        return new TestCasePage(driver);
    }

    public DeleteTestCaseModal clickDeleteTestCaseButton() {
        log.info("Click 'Delete' test case button");
        click(DELETE_TEST_CASE_BUTTON);
        return new DeleteTestCaseModal(driver);
    }
}
