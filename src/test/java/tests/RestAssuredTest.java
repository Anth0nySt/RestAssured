package tests;

import models.CreateBody;
import models.CreateResponseBody;
import models.LoginBody;
import models.ResponseBody;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpecs.*;

@Tag("forJenkins")
public class RestAssuredTest extends TestBase {

    @Test
    void checkSingleResourceTest() {
        given()
                .filter(withCustomTemplates())
                .spec(RegRequestSpec200)
                .when()
                .get("/unknown/2")
                .then()
                .spec(RegResponseSpec200)
                .body("data", is(notNullValue()));

    }

    @Test
    void checkSingleUserNotFoundTest() {
        given()
                .filter(withCustomTemplates())
                .spec(RegRequestSpec200)
                .when()
                .get("/users/23")
                .then()
                .spec(BadResponseSpec404);

    }

    @Test
    void checkRegistrationTest() {

        LoginBody regData = new LoginBody();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");

        ResponseBody responseData = step("Registration", () ->
                given()
                        .filter(withCustomTemplates())
                        .spec(RegRequestSpec200)
                        .body(regData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(RegResponseSpec200)
                        .extract().as(ResponseBody.class));
        step("Success registration", () -> {
            assertThat(responseData.getToken(), notNullValue());
            assertEquals("4", responseData.getId());
        });
    }


    @Test
    void checkUpdateUserInfoTest() {
        CreateBody userinfo = new CreateBody();
        userinfo.setName("morpheus");
        userinfo.setJob("leader");


        CreateResponseBody response = step("Update user info", () ->
                given()
                        .filter(withCustomTemplates())
                        .body(userinfo)
                        .spec(RegRequestSpec200)
                        .when()
                        .post("/users")
                        .then()
                        .spec(UpdResponseSpec201)
                        .extract().as(CreateResponseBody.class));
        step("Success update", () -> {
            assertEquals("morpheus", userinfo.getName());
            assertEquals("leader", userinfo.getJob());
            assertThat(response.getId(), notNullValue());
            assertThat(response.getCreatedAt(),notNullValue());
        });
    }

    @Test
    void checkDeleteUserInfoTest() {
        String userInfo = "";

        given()
                .filter(withCustomTemplates())
                .when()
                .body(userInfo)
                .spec(RegRequestSpec200)
                .delete("users/2")
                .then()
                .spec(DeleteResponseSpec204);
    }
}