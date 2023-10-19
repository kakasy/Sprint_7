package api.client;

import api.model.Courier;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class CourierClient {
    @Step("Send POST request to /api/v1/courier/login")
    @DisplayName("Логин курьера в системе")
    public Response sendPostRequestApiV1CourierLogin(Courier courier){
        return RestAssured.given()
                .log().all()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Send POST request to /api/v1/courier")
    @DisplayName("Создание курьера")
    public Response sendPostRequestApiV1CourierCreate(Courier courier){
        return RestAssured.given()
                .log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

}
