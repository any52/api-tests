package spec;

import io.restassured.builder.ResponseSpecBuilder;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class Spec {

    public static RequestSpecification commonRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification commonResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();

    public static RequestSpecification userOrLoginRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification userCreateResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(201)
            .expectBody("name", notNullValue())
            .expectBody("job", notNullValue())
            .build();

    public static ResponseSpecification userUpdateResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("name", notNullValue())
            .expectBody("job", notNullValue())
            .build();

    public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("token", notNullValue())
            .build();

}
