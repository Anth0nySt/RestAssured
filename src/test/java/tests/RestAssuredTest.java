package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import models.LoginBody;
import models.ResponseBody;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpecs.*;

@Tag("forJenkins")
public class RestAssuredTest extends TestBase {

    @Test
    void checkSingleResource() {
        given()
                .spec(RegRequestSpec)
                .when()
                .get("/unknown/2")
                .then()
                .spec(RegResponseSpec);

    }

    @Test
    void checkSingleUserNotFound() {
        given()
                .spec(RegRequestSpec)
                .when()
                .get("/users/23")
                .then()
                .spec(BadResponseSpec)
                .statusCode(404);

    }

    @Test
    void checkRegistration() {

        LoginBody regData = new LoginBody();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");

        ResponseBody responseData = step("Registration", () ->
                given()
                        .filter(new AllureRestAssured())
                        .spec(RegRequestSpec)
                        .body(regData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(RegResponseSpec)
                        .extract().as(ResponseBody.class));
        step("Success registration", () ->
                assertEquals("QpwL5tke4Pnpja7X4", responseData.getToken()));
        assertEquals("4", responseData.getId());
    }


    @Test
    void checkUpdateUserInfo() {
        String userInfo = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";

        given()
                .when()
                .body(userInfo)
                .spec(RegRequestSpec)
                .post("/users/2")
                .then()
                .spec(UpdResponseSpec);
    }

    @Test
    void checkDeleteUserInfo() {
        String userInfo = "";

        given()
                .when()
                .body(userInfo)
                .spec(RegRequestSpec)
                .delete("users/2")
                .then()
                .spec(DeleteResponseSpec);
    }
}