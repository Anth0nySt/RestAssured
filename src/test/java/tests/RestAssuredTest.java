package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {

    @Test
    void checkSingleResource() {
        given()
                .when()
                .log().uri()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().body()
                .body("data", is(notNullValue()))
                .body("data.id", is(2))
                .body("data.name", is("fuchsia rose"));

    }

    @Test
    void checkSingleUserNotFound() {
        given()
                .when()
                .log().uri()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    void checkRegistration() {
        String regData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .when()
                .body(regData)
                .contentType(JSON)
                .log().uri()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }

    @Test
    void checkUpdateUserInfo() {
        String userInfo = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";

        given()
                .when()
                .body(userInfo)
                .contentType(JSON)
                .log().uri()
                .post("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .statusCode(201);
    }

    @Test
    void checkDeleteUserInfo() {
        String userInfo = "";

        given()
                .when()
                .body(userInfo)
                .contentType(JSON)
                .log().uri()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .statusCode(204);
    }
}