package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
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
}
