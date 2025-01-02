package tests;

import org.testng.annotations.Test;
import tests.base.BaseTest;

public class ProjectTest extends BaseTest {

    @Test
    public void createProjectTest() {
        driver.get("https://app.qase.io/projects");
    }
}
