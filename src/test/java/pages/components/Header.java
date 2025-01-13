package pages.components;

import org.openqa.selenium.WebDriver;
import pages.base.BaseElement;

public class Header extends BaseElement {

    public Header(WebDriver driver) {
        super(driver);
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    private static final String HEADER_WRAPPER = "//header[@role='banner']";
    private static final String PROJECTS_BUTTON = HEADER_WRAPPER + "//a[contains(text(),'Projects')]";

    /* **************************************
     *************** Methods ****************
     ****************************************/

    public void clickProjectsButtonOnHeader() {
        click(PROJECTS_BUTTON);
    }
}
