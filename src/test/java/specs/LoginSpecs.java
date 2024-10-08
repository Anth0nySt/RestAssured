package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class LoginSpecs {
    public static RequestSpecification RegRequestSpec200 = with()
            .contentType(JSON)
            .log().all();


    public static ResponseSpecification RegResponseSpec200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();

    public static ResponseSpecification BadResponseSpec404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(STATUS)
            .log(ALL)
            .build();

    public static ResponseSpecification UpdResponseSpec201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();

    public static ResponseSpecification DeleteResponseSpec204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .build();
}
