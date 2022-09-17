import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends DeleteAndCreateMethods {

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        createAccount();
    }

    @DisplayName("Check status code 409 and message in request with duplicate login field")
    @Test
    public void requestWithDuplicateLogin(){
        Courier duplicateLogin = new Courier(existingLogin, existingLoginPassword, existingLoginFirstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(duplicateLogin)
                        .when()
                        .post(API_COURIER);
        response.then().log().all();
        response.then().assertThat().statusCode(409);
        response.then().assertThat().body("message",
                equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Check status code 400 and message in request without login field")
    @Test
    public void requestWithoutLogin(){
        Courier emptyLoginField = new Courier(null, existingLoginPassword, existingLoginFirstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(emptyLoginField)
                        .when()
                        .post(API_COURIER);
        response.then().log().all();
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message",
                equalTo("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Check status code 400 and message in request without password field")
    @Test
    public void requestWithoutPassword(){
        Courier emptyLoginField = new Courier(existingLogin, null, existingLoginFirstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(emptyLoginField)
                        .when()
                        .post(API_COURIER);
        response.then().log().all();
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message",
                equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown(){
        deleteLogin();
    }


}
