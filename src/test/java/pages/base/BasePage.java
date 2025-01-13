package pages.base;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import pages.ProjectsPage;
import pages.components.Header;

@Log4j2
public abstract class BasePage extends BaseElement {

    protected Header header;

    public BasePage(WebDriver driver) {
        super(driver);
        header = new Header(driver);
    }

    protected abstract void pageLoading();

    public ProjectsPage clickProjectsButtonOnHeader() {
        log.info("Click 'Projects' button on header");
        header.clickProjectsButtonOnHeader();
        return new ProjectsPage(driver);
    }
}
