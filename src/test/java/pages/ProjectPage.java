package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.base.BasePage;

@Log4j2
public class ProjectPage extends BasePage {
    public ProjectPage(WebDriver driver) {
        super(driver);
        waitForDOMLoaded();
        pageLoading();
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String PROJECT_WRAPPER = "(//div[@id='layout']/div)[2]";
    private static final String PROJECT_NAME_TITLE = PROJECT_WRAPPER + "//div[@id='application-content']//h1";
    private static final String CREATE_TEST_CASE_BUTTON = PROJECT_WRAPPER + "//button[@id='create-case-button']";
    private static final String SUITES_AND_TEST_CASES_WRAPPER = PROJECT_WRAPPER + "//div[@id='suitecases-container']";
    private static final String SPECIFIC_TEST_CASE = SUITES_AND_TEST_CASES_WRAPPER + "//*[text()=%s]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    @Override
    protected void pageLoading() {
        log.info("Loading Project Page");
        try {
            if (!(isVisible(PROJECT_NAME_TITLE, 0, timeout))) throw new Exception();
        } catch (Exception ex) {
            throw new AssertionError("Project Page was not loaded");
        }
    }

    public TestCasePage clickCreateTestCase() {
        log.info("Click '+Case' button");
        click(CREATE_TEST_CASE_BUTTON);
        waitForInvisibility(CREATE_TEST_CASE_BUTTON);
        return new TestCasePage(driver);
    }

    public boolean isSpecificTestCaseVisible(String testCaseName) {
        return isVisible(String.format(SPECIFIC_TEST_CASE, Quotes.escape(testCaseName)));
    }
}
