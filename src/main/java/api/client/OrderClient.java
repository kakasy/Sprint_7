package api.client;


import api.model.Order;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class OrderClient {

    private static final String ENDPOINT = "/api/v1/orders";

    @Step("Send POST request to /api/v1/orders")
    @DisplayName("Создание заказа")
    public Response sendPostRequestApiV1OrdersCreate(Order order) {
        return RestAssured.given()
                .log().all()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ENDPOINT);
    }
}
