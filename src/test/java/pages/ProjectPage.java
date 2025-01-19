package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.base.BasePage;
import pages.components.CreateSuiteModal;
import pages.components.DeleteSuiteModal;
import pages.components.EditSuiteModal;
import pages.components.TestCaseInfoSidePanel;

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
    private static final String CREATE_SUITE_BUTTON = PROJECT_WRAPPER + "//button[@id='create-suite-button']";
    private static final String SUITES_AND_TEST_CASES_WRAPPER = PROJECT_WRAPPER + "//div[@id='suitecases-container']";
    private static final String SPECIFIC_TEST_CASE = SUITES_AND_TEST_CASES_WRAPPER +
            "//div[@data-suite-body-id and @draggable]//*[text()=%s]";
    private static final String SPECIFIC_SUITE_NAME = SUITES_AND_TEST_CASES_WRAPPER +
            "//div[@data-suite-body-id and not(@draggable)]//*[text()=%s]";
    private static final String EDIT_SPECIFIC_SUITE_BUTTON = SPECIFIC_SUITE_NAME + "/..//button[@aria-label='Edit suite']";
    private static final String DELETE_SPECIFIC_SUITE_BUTTON = SPECIFIC_SUITE_NAME + "/..//button[@aria-label='Delete suite']";

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

    public TestCasePage clickCreateTestCaseButton() {
        log.info("Click '+Case' button");
        click(CREATE_TEST_CASE_BUTTON);
        waitForInvisibility(CREATE_TEST_CASE_BUTTON);
        return new TestCasePage(driver);
    }

    public CreateSuiteModal clickCreateSuiteButton() {
        log.info("Click '+Suite' button");
        click(CREATE_SUITE_BUTTON);
        return new CreateSuiteModal(driver);
    }

    public TestCaseInfoSidePanel clickSpecificTestCase(String testCaseName) {
        log.info("Click '{}' test case", testCaseName);
        click(String.format(SPECIFIC_TEST_CASE, Quotes.escape(testCaseName)));
        return new TestCaseInfoSidePanel(driver);
    }

    public EditSuiteModal clickEditSpecificSuiteButton(String suiteName) {
        log.info("Click Edit '{}' suite", suiteName);
        click(String.format(EDIT_SPECIFIC_SUITE_BUTTON, Quotes.escape(suiteName)));
        return new EditSuiteModal(driver);
    }

    public DeleteSuiteModal clickDeleteSpecificSuiteButton(String suiteName) {
        log.info("Click Delete '{}' suite", suiteName);
        click(String.format(DELETE_SPECIFIC_SUITE_BUTTON, Quotes.escape(suiteName)));
        return new DeleteSuiteModal(driver);
    }

    public boolean isSpecificTestCaseVisible(String testCaseName) {
        return isVisible(String.format(SPECIFIC_TEST_CASE, Quotes.escape(testCaseName)));
    }

    public boolean isSpecificTestCaseInvisible(String testCaseName) {
        return isInvisible(String.format(SPECIFIC_TEST_CASE, Quotes.escape(testCaseName)));
    }

    public boolean isSpecificSuiteVisible(String suiteName) {
        return isVisible(String.format(SPECIFIC_SUITE_NAME, Quotes.escape(suiteName)));
    }

    public boolean isSpecificSuiteInvisible(String suiteName) {
        return isInvisible(String.format(SPECIFIC_SUITE_NAME, Quotes.escape(suiteName)));
    }
}
