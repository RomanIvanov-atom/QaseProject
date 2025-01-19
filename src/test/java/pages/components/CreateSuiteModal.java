package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.ProjectPage;
import pages.base.BaseElement;
import wrappers.Input;

@Log4j2
public class CreateSuiteModal extends BaseElement {
    public CreateSuiteModal(WebDriver driver) {
        super(driver);
        log.info("Opening create suite modal");
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String CREATE_SUITE_MODAL_WRAPPER = "//h3[text()='Create suite']/ancestor::div[@id='modals']//dialog";
    private static final String CREATE_BUTTON = CREATE_SUITE_MODAL_WRAPPER + "//span[contains(text(),'Create')]";
    private static final String SPECIFIC_INPUT_FIELD = CREATE_SUITE_MODAL_WRAPPER +
            "//label[contains(text(),%s)]//ancestor::div[2]//div[contains(@aria-label,'input')]//div[@contenteditable='true']";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    public ProjectPage createNewSuite(String suiteName, String description, String preconditions) {
        log.info("Creating new suite with '{}' name", suiteName);
        new Input(driver, "Suite name").write(suiteName);
        sendKeys(String.format(SPECIFIC_INPUT_FIELD, Quotes.escape("Description")), description);
        sendKeys(String.format(SPECIFIC_INPUT_FIELD, Quotes.escape("Preconditions")), preconditions);
        clickCreateSuiteButton();
        return new ProjectPage(driver);
    }

    private void clickCreateSuiteButton() {
        log.info("Click 'Delete project' button");
        click(CREATE_BUTTON);
        waitForInvisibility(CREATE_SUITE_MODAL_WRAPPER);
    }
}
