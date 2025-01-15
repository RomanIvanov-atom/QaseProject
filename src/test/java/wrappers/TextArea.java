package wrappers;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.base.BaseElement;

@Log4j2
public class TextArea extends BaseElement {

    private static final String SPECIFIC_TEXT_AREA = "//label[contains(text(),%s)]/ancestor::*[contains(@class,'form-group')]" +
            "//div[contains(@class, 'toastui-editor-content') and @contenteditable]";
    private final String label;

    public TextArea(WebDriver driver, String label) {
        super(driver);
        this.label = label;
    }

    public void write(String text) {
        log.info("Writing '{}' into '{}' text area", text, label);
        sendKeys(String.format(SPECIFIC_TEXT_AREA, Quotes.escape(label)), text);
    }

    public String getText() {
        log.info("Getting text from '{}' text area field", label);
        return getText(String.format(SPECIFIC_TEXT_AREA, Quotes.escape(label)) + "//p");
    }
}
