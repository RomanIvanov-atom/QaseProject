package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.ProjectPage;
import pages.base.BaseElement;
import wrappers.Input;

@Log4j2
public class EditSuiteModal extends BaseElement {
    public EditSuiteModal(WebDriver driver) {
        super(driver);
        log.info("Opening edit suite modal");
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String EDIT_SUITE_MODAL_WRAPPER = "//h3[text()='Edit suite']/ancestor::div[@id='modals']//dialog";
    private static final String SAVE_BUTTON = EDIT_SUITE_MODAL_WRAPPER + "//span[contains(text(),'Save')]";
    private static final String SPECIFIC_INPUT_FIELD = EDIT_SUITE_MODAL_WRAPPER +
            "//label[contains(text(),%s)]//ancestor::div[2]//div[contains(@aria-label,'input')]//div[@contenteditable='true']";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    public ProjectPage editSuite(String suiteName, String description, String preconditions) {
        log.info("Editing suite with '{}' name", suiteName);
        new Input(driver, "Suite name").write(suiteName);
        sendKeys(String.format(SPECIFIC_INPUT_FIELD, Quotes.escape("Description")), description);
        sendKeys(String.format(SPECIFIC_INPUT_FIELD, Quotes.escape("Preconditions")), preconditions);
        clickSaveSuiteButton();
        return new ProjectPage(driver);
    }

    private void clickSaveSuiteButton() {
        log.info("Click 'Save' button");
        click(SAVE_BUTTON);
        waitForInvisibility(EDIT_SUITE_MODAL_WRAPPER);
    }

    public String getTextFromSpecificInputField(String field) {
        return new Input(driver, field).getText();
    }
}
