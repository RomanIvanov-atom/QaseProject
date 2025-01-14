package wrappers;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Quotes;
import pages.base.BaseElement;

@Log4j2
public class Dropdown extends BaseElement {

    private static final String SPECIFIC_DROPDOWN = "(//label[contains(text(),%s)]/..//input)[1]";
    private static final String SPECIFIC_DROPDOWN_OPTION = "//div[@id='modals']/div[last()]//div[@role='option' and text()=%s]";
    private final String label;

    public Dropdown(WebDriver driver, String label) {
        super(driver);
        this.label = label;
    }

    public void select(String option) {
        log.info("Selecting '{}' from '{}' dropdown", option, label);
        click(String.format(SPECIFIC_DROPDOWN, Quotes.escape(label)));
        click(String.format(SPECIFIC_DROPDOWN_OPTION, Quotes.escape(option)));
    }
}
