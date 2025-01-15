package api.adapters;

import api.adapters.base.BaseAPI;
import api.models.testCase.CreateTestCaseRq;
import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestCaseAPI extends BaseAPI {

    @Description("Create test case through API")
    public static void createTestCase(String projectCode, String titleTestCase, String descriptionTestCase, String preConditions,
                                      String postConditions) {
        log.info("Creating test case with name '{}' for project with code '{}' through API", titleTestCase, projectCode);
        CreateTestCaseRq createTestCaseBody = CreateTestCaseRq.builder()
                .title(titleTestCase)
                .description(descriptionTestCase)
                .preconditions(preConditions)
                .postconditions(postConditions)
                .build();
        String path = "/case/{project_code}".replace("{project_code}", projectCode);
        createPostRequest(createTestCaseBody, path, 200);
    }

//    @Description("Delete test case through API")
//    public static void deleteTestCase(String projectCode) {
//        log.info("Deleting test case with code {} through API", projectCode);
//        given()
//                .baseUri(URL)
//                .spec(spec)
//                .when()
//                .delete("/case/{project_code}/{test_case_id}", projectCode, testCaseId)
//                .then()
//                .log().all()
//                .statusCode(200);
//    }

//    public void getAllTestCases(String projectCode) {
//        given()
//                .baseUri(URL)
//                .spec(spec)
//                .when()
//                .delete("/case/{project_code}", projectCode)
//                .then()
//                .log().all()
//                .statusCode(200);
//    }
}