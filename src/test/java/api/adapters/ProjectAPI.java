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
    public final String token = Constants.API_TOKEN;
    public final String URL = Constants.BASE_API_URL;

    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public RequestSpecification spec =
            given()
                    .contentType(ContentType.JSON)
                    .header("Token", token);

    public void createProject(CreateProjectRq createProjectRq) {
        given()
                .spec(spec)
                .body(gson.toJson(createProjectRq))
                .when()
                .post(URL)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Description("Delete project through API")
    public void deleteProject(String projectCode) {
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