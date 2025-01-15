package api.adapters;

import api.adapters.base.BaseAPI;
import api.models.project.CreateProjectRq;
import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j2;

import static io.restassured.RestAssured.given;

@Log4j2
public class ProjectAPI extends BaseAPI {

    @Description("Create project through API")
    public static void createProject(String projectName, String projectCode) {
        log.info("Creating project with name '{}' and code '{}' through API", projectName, projectCode);
        CreateProjectRq createProjectBody = CreateProjectRq.builder()
                .title(projectName)
                .code(projectCode)
                .build();
        createPostRequest(createProjectBody, "/project", 200);
    }

    @Description("Delete project through API")
    public static void deleteProject(String projectCode) {
        log.info("Deleting project with code {} through API", projectCode);
        given()
                .baseUri(URL)
                .spec(spec)
                .when()
                .delete("/project/{project_code}", projectCode)
                .then()
                .log().all()
                .statusCode(200);
    }

    public void getProject(String projectCode) {
        given()
                .spec(spec)
                .when()
                .get(URL + projectCode)
                .then()
                .log().all()
                .statusCode(200);
    }
}