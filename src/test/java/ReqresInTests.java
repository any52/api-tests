import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInTests {

    public static final String BASE_URL = "https://reqres.in";

    @Test
    @DisplayName("Check that status code of request of list existing users is 200 OK ")
    void getListUsersTest() {
        given()
                .log().all()
                .when()
                .get(BASE_URL + "/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("Check that status code of request of list existing resources is 200 OK ")
    void getListResourcesTest() {
        given()
                .log().all()
                .when()
                .get(BASE_URL + "/api/unknown")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("Check that status code of request of non-existent resource is 404 Not Found ")
    void resourceNotFoundTest() {
        given()
                .log().all()
                .when()
                .get(BASE_URL + "/api/unknown/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    @DisplayName("Check status code, name and job of request creating user")
    void createUserTest() {
        String user = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(user)
                .when()
                .post(BASE_URL + "/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    @DisplayName("Check status code and token of request registration user")
    void registerUserTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post(BASE_URL + "/api/register")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Check status code, name and job of request updating user")
    void updateUserTest() {
        String user = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(user)
                .when()
                .put(BASE_URL + "/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @DisplayName("Check that status code of request deleting user is 204 OK")
    void deleteUserTest() {
        given()
                .log().all()
                .when()
                .delete(BASE_URL + "/api/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

}
