package wrappers;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.base.BaseElement;

@Log4j2
public class Input extends BaseElement {

    private static final String SPECIFIC_INPUT = "//label[contains(text(),%s)]//ancestor::div[2]//input";
    private final String label;

    public Input(WebDriver driver, String label) {
        super(driver);
        this.label = label;
    }

    public void write(String text) {
        log.info("Writing '{}' into '{}'", text, label);
        sendKeys(String.format(SPECIFIC_INPUT, Quotes.escape(label)), text);
    }

    public String getText() {
        log.info("Getting text from '{}' input field", label);
        return getAttribute(String.format(SPECIFIC_INPUT, Quotes.escape(label)), 0, "value");
    }
}
