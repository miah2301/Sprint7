import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateRanomCourieTest extends Constans{

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @DisplayName("Check status code 201 and body in create random account")
    @Test
    public void randomAccountCreation(){
        Courier successfulRandomCourier = Courier.getRandomCourier();
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(successfulRandomCourier)
                        .when()
                        .post(API_COURIER);
        response.then().log().all();
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("ok", equalTo(true));
    }
}
