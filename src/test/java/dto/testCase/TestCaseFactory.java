package dto.testCase;

import com.github.javafaker.Faker;
import org.openqa.selenium.support.ui.Quotes;

public class TestCaseFactory {
    public static TestCase getFilledAccount(String title, String description, String preConditions, String postConditions) {
        Faker faker = new Faker();
        return TestCase.builder()
                .title(title)
                .status(faker.options().option("Actual", "Draft", "Deprecated"))
                .description(description)
                .severity(faker.options().option(Quotes.escape("Not set"), "Blocker", "Critical", "Major", "Normal", "Minor", "Trivial"))
                .priority(faker.options().option("Not set", "High", "Medium", "Low"))
                .type(faker.options().option("Other", "Functional", "Smoke", "Regression", "Exploratory"))
                .layer(faker.options().option("Not set", "E2E", "API", "Unit"))
                .behavior(faker.options().option("Not set", "Positive", "Negative", "Destructive"))
                .preConditions(preConditions)
                .postConditions(postConditions)
                .build();
    }
}
