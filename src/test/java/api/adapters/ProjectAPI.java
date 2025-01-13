package api.adapters;

import api.models.CreateProjectRq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import utils.constants.Constants;

import static io.restassured.RestAssured.given;

@Log4j2
public class ProjectAPI {

    public static final String token = Constants.API_TOKEN;
    public static final String URL = Constants.BASE_API_URL;

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static RequestSpecification spec =
            given()
                    .contentType(ContentType.JSON)
                    .header("Token", token);

    @Description("Ccreate project through API")
    public static void createProject(String projectName, String projectCode) {
        log.info("Create project with name '{}' and code '{}' through API", projectName, projectCode);
        CreateProjectRq createProjectBody = CreateProjectRq.builder()
                .title(projectName)
                .code(projectCode)
                .build();
        given()
                .spec(spec)
                .body(gson.toJson(createProjectBody))
                .when()
                .post(URL)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Description("Delete project through API")
    public static void deleteProject(String projectCode) {
        log.info("Deleting project with code {} through API", projectCode);
        given()
                .spec(spec)
                .when()
                .delete(URL + projectCode)
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