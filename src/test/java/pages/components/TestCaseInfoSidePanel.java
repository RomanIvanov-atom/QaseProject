package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.TestCasePage;
import pages.base.BaseElement;

@Log4j2
public class TestCaseInfoSidePanel extends BaseElement {

    public TestCaseInfoSidePanel(WebDriver driver) {
        super(driver);
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String PANEL_WRAPPER = "//*[@id='suitecases-container']/div[2]";
    private static final String EDIT_TEST_CASE_BUTTON = PANEL_WRAPPER + "//span[contains(text(),'Edit')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    public TestCasePage clickEditTestCaseButton() {
        log.info("Click 'Edit' test case button");
        click(EDIT_TEST_CASE_BUTTON);
        waitForInvisibility(PANEL_WRAPPER);
        return new TestCasePage(driver);
    }
}
