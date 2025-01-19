package api.adapters;

import api.adapters.base.BaseAPI;
import api.models.suite.CreateSuiteRq;
import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SuiteAPI extends BaseAPI {

    @Description("Create suite through API")
    public static void createSuite(String projectCode, String titleSuite, String descriptionSuite, String preConditionsSuite) {
        log.info("Creating suite with name '{}' for project with code '{}' through API", titleSuite, projectCode);
        CreateSuiteRq createSuiteBody = CreateSuiteRq.builder()
                .title(titleSuite)
                .description(descriptionSuite)
                .preconditions(preConditionsSuite)
                .build();
        String path = "/suite/{project_code}".replace("{project_code}", projectCode);
        createPostRequest(createSuiteBody, path, 200);
    }
}