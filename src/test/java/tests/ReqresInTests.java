package tests;

import model.LoginBodyModel;
import model.LoginResponseModel;
import model.UserModelForCreateRequest;
import model.UserModelForUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static spec.Spec.*;

public class ReqresInTests {

    @Test
    @DisplayName("Check that status code of request of list existing users is 200 OK")
    void getListUsersTest() {
        step("Check that status code of request of list existing users is 200 OK", () -> {
            given(commonRequestSpec)
                    .when()
                    .get("/users?page=2")
                    .then()
                    .spec(commonResponseSpec);
        });
    }

    @Test
    @DisplayName("Check that status code of request of list existing resources is 200 OK")
    void getListResourcesTest() {
        step("Check that status code of request of list existing resources is 200 OK", () -> {
            given(commonRequestSpec)
                    .when()
                    .get("/unknown")
                    .then()
                    .spec(commonResponseSpec);
        });
    }

    @Test
    @DisplayName("Check that status code of request of non-existent resource is 404 Not Found ")
    void resourceNotFoundTest() {
        step("Check that status code of request of non-existent resources is 404 Not Found", () -> {
            given(commonRequestSpec)
                    .when()
                    .get("/unknown/23")
                    .then()
                    .log().all()
                    .statusCode(404);
        });
    }

    @Test
    @DisplayName("Check status code, name and job of request creating user")
    void createUserTest() {

        UserModelForCreateRequest userModel = new UserModelForCreateRequest();
        userModel.setName("morpheus");
        userModel.setJob("leader");
        UserModelForCreateRequest userResponse = step("Check status code of request creating user", () -> {
            return given(userOrLoginRequestSpec)
                    .body(userModel)
                    .when()
                    .post("/users")
                    .then()
                    .spec(userCreateResponseSpec)
                    .extract().as(UserModelForCreateRequest.class);
        });
        step("Check name and job of request creating user", () -> {
            assertThat(userResponse.getName()).isEqualTo("morpheus");
            assertThat(userResponse.getJob()).isEqualTo("leader");
        });
    }

    @Test
    @DisplayName("Check status code and token of request registration user")
    void registerUserTest() {

        LoginBodyModel loginBodyModel = new LoginBodyModel();
        loginBodyModel.setEmail("eve.holt@reqres.in");
        loginBodyModel.setPassword("pistol");
        LoginResponseModel loginResponse = step("Check status code of request registration user", () -> {
            return given(userOrLoginRequestSpec)
                    .body(loginBodyModel)
                    .when()
                    .post("/register")
                    .then()
                    .spec(loginResponseSpec)
                    .extract().as(LoginResponseModel.class);
        });
        step("Check token of request registration user", () -> {
            assertThat(loginResponse.getToken()).isNotNull();
        });
    }

    @Test
    @DisplayName("Check status code, name and job of request updating user")
    void updateUserTest() {

        UserModelForUpdateRequest userModel = new UserModelForUpdateRequest();
        userModel.setName("morpheus");
        userModel.setJob("zion resident");
        UserModelForUpdateRequest userResponse = step("Check status code of request updating user", () -> {
            return given(userOrLoginRequestSpec)
                    .body(userModel)
                    .when()
                    .put("/users/2")
                    .then()
                    .spec(userUpdateResponseSpec)
                    .extract().as(UserModelForUpdateRequest.class);
        });
        step("Check name and job of request updating user", () -> {
            assertThat(userResponse.getName()).isEqualTo("morpheus");
            assertThat(userResponse.getJob()).isEqualTo("zion resident");
        });

    }

    @Test
    @DisplayName("Check that status code of request deleting user is 204 No Content ")
    void deleteUserTest() {
        step("Check that status code of request deleting user is 204 No Content ", () -> {
            given(commonRequestSpec)
                    .when()
                    .delete("/users/2")
                    .then()
                    .log().all()
                    .statusCode(204);
        });
    }

    @Test
    @DisplayName("Check that status code of request of list existing users, " +
            "total number of users, user by email")
    void getListUsersTestUsingGroovy() {
        step("Check that status code of request of list existing users, total of users, " +
                "user by email", () -> {
            given(commonRequestSpec)
                    .when()
                    .get("/users?page=2")
                    .then()
                    .spec(commonResponseSpec)
                    .body("total", is(12))
                    .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                            hasItem("michael.lawson@reqres.in"));

        });
    }

    @Test
    @DisplayName("Check that status code of request of list existing resources, name resource with id = 1")
    void getListResourcesTestUsingGroovy() {
        step("Check that status code of request of list existing resources, name resource with id = 1", () -> {
            given(commonRequestSpec)
                    .when()
                    .get("/unknown")
                    .then()
                    .spec(commonResponseSpec)
                    .body("data.find{it.id == 1}.name", is("cerulean"));
        });
    }

    @Test
    @DisplayName("Check status code, name and job of request creating user")
    void createUserTestUsingBodyVerification() {

        UserModelForCreateRequest userModel = new UserModelForCreateRequest();
        userModel.setName("morpheus");
        userModel.setJob("leader");
        step("Check status code, name and job of request creating user", () -> {
            return given(userOrLoginRequestSpec)
                    .body(userModel)
                    .when()
                    .post("/users")
                    .then()
                    .spec(userCreateResponseSpec)
                    .body("name", is("morpheus"))
                    .body("job", is("leader"));
        });
    }


    @Test
    @DisplayName("Check status code, name and job of request updating user")
    void updateUserTestUsingBodyVerification() {

        UserModelForUpdateRequest userModel = new UserModelForUpdateRequest();
        userModel.setName("morpheus");
        userModel.setJob("zion resident");
        step("Check status code, name and job of request updating user", () -> {
            return given(userOrLoginRequestSpec)
                    .body(userModel)
                    .when()
                    .put("/users/2")
                    .then()
                    .spec(userUpdateResponseSpec)
                    .body("name", is("morpheus"))
                    .body("job", is("zion resident"));

        });
    }

}