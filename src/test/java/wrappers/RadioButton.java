package wrappers;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.base.BaseElement;

@Log4j2
public class RadioButton extends BaseElement {

    private static final String SPECIFIC_RADIOBUTTON = "//label[contains(text(),%s)]//ancestor::div[2]//span[contains(text(),%s)]" +
            "/preceding::input[@type='radio'][1]";
    private final String label;

    public RadioButton(WebDriver driver, String label) {
        super(driver);
        this.label = label;
    }

    public void select(String option) {
        log.info("Selecting '{}' option as '{}'", option, label);
        click(String.format(SPECIFIC_RADIOBUTTON, Quotes.escape(label), Quotes.escape(option)));
    }
}
