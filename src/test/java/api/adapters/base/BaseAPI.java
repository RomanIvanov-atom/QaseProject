package api.adapters.base;

import api.models.base.Requestable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import utils.constants.Constants;

import static io.restassured.RestAssured.given;

@Log4j2
public class BaseAPI {

    public static final String token = Constants.API_TOKEN;
    public static final String URL = Constants.BASE_API_URL;

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static RequestSpecification spec =
            given()
                    .contentType(ContentType.JSON)
                    .header("Token", token);

    public static void createPostRequest(Requestable body, String path, int expectedCode) {
        log.info("Response info (Headers and body):\n");
        given()
                .baseUri(URL)
                .spec(spec)
                .body(gson.toJson(body))
                .when()
                .post(path)
                .then()
                .log().all()
                .statusCode(expectedCode);
    }

    public static void createDeleteRequest(String path, int expectedCode) {
        log.info("Response info (Headers and body):\n");
        given()
                .baseUri(URL)
                .spec(spec)
                .when()
                .delete(path)
                .then()
                .log().all()
                .statusCode(expectedCode);
    }
}
