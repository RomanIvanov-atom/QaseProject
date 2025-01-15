package pages;

import dto.testCase.TestCase;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;
import wrappers.Dropdown;
import wrappers.Input;
import wrappers.TextArea;

@Log4j2
public class TestCasePage extends BasePage {
    public TestCasePage(WebDriver driver) {
        super(driver);
        waitForDOMLoaded();
        pageLoading();
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String TEST_CASE_WRAPPER = "(//div[@id='layout']/div)[2]";
    private static final String PAGE_TITLE = TEST_CASE_WRAPPER + "//h1[text()='Create test case' or text()='Edit test case']";
    private static final String CREATE_TEST_CASE_BUTTON = TEST_CASE_WRAPPER + "//button[@type='submit']//span[(text()='Save')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    @Override
    protected void pageLoading() {
        log.info("Loading Test case Page");
        try {
            if (!(isVisible(PAGE_TITLE, 0, timeout))) throw new Exception();
        } catch (Exception ex) {
            throw new AssertionError("Test case Page was not loaded");
        }
    }

    public TestCasePage fillAllTestCaseFields(TestCase testCase) {
        log.info("Filling all fields for test case '{}'", testCase.getTitle());
        new Input(driver, "Title").write(testCase.getTitle());
        new Dropdown(driver, "Status").select(testCase.getStatus());
        new TextArea(driver, "Description").write(testCase.getDescription());
        new Dropdown(driver, "Severity").select(testCase.getSeverity());
        new Dropdown(driver, "Priority").select(testCase.getPriority());
        new Dropdown(driver, "Type").select(testCase.getType());
        new Dropdown(driver, "Layer").select(testCase.getLayer());
        new Dropdown(driver, "Behavior").select(testCase.getBehavior());
        new TextArea(driver, "Pre-conditions").write(testCase.getPreConditions());
        new TextArea(driver, "Post-conditions").write(testCase.getPostConditions());
        return this;
    }

    public TestCasePage fillBasicTestCaseFields(TestCase testCase) {
        log.info("Filling Title, Description, Pre- and Post-condition fields for test case '{}'", testCase.getTitle());
        new Input(driver, "Title").write(testCase.getTitle());
        new TextArea(driver, "Description").write(testCase.getDescription());
        new TextArea(driver, "Pre-conditions").write(testCase.getPreConditions());
        new TextArea(driver, "Post-conditions").write(testCase.getPostConditions());
        return this;
    }

    public String getTextFromSpecificInputField(String field) {
        return new Input(driver, field).getText();
    }

    public String getTextFromSpecificTextAreaField(String field) {
        return new TextArea(driver, field).getText();
    }

    public ProjectPage clickSave() {
        log.info("Click 'Save' button");
        click(CREATE_TEST_CASE_BUTTON);
        waitForInvisibility(PAGE_TITLE);
        return new ProjectPage(driver);
    }
}
