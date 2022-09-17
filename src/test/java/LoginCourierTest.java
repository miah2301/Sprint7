import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class LoginCourierTest extends DeleteAndCreateMethods {

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        createAccount();
    }

    @DisplayName("Check status code 200 and id value in successful login ")
    @Test
    public void successfulLogin(){
        Login dataCourier = new Login(existingLogin, existingLoginPassword);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(dataCourier)
                        .when()
                        .post(API_LOGIN);
        response.then().log().all();
        response.then().assertThat().statusCode(200);
        MatcherAssert.assertThat("id", notNullValue());
    }

    @DisplayName("Check status code 400 and message in request without login field")
    @Test
    public void requestWithoutLogin(){
        Login dataCourierWithoutLogin = new Login(null, existingLoginPassword);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(dataCourierWithoutLogin)
                        .when()
                        .post(API_LOGIN);
        response.then().log().all();
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Check status code 404 and message in non-existent data")
    @Test
    public void nonExistentData(){
        Login loginData = Login.getRandomLoginData();
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(loginData)
                        .when()
                        .post(API_LOGIN);
        response.then().log().all();
        response.then().assertThat().statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown(){
        deleteLogin();
    }
}
