import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteAndCreateMethods extends Constans{

    public void deleteLogin(){
        Login login = new Login(existingLogin, existingLoginPassword);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post("/api/v1/courier/login");
        String id = response.jsonPath().getString("id");
        given()
                .when()
                .delete("/api/v1/courier/" + id);
    }
    public void createAccount(){
        Courier successfulCourier = new Courier(existingLogin, existingLoginPassword, existingLoginFirstName);
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(successfulCourier)
                        .when()
                        .post(API_COURIER);
    }
}
