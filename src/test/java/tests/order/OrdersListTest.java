package tests.order;

import api.model.OrdersList;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrdersListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов по /api/v1/orders")
    public void getListOrdersTest(){
        RestAssured.given()
                .log().all()
                .header("Content-type", "application/json")
                .get("/api/v1/orders")
                .then()
                .assertThat()
                .statusCode(200);

        OrdersList listOfOrders = RestAssured.given()
                .header("Content-type", "application/json")
                .log().all()
                .get("/api/v1/orders")
                .body()
                .as(OrdersList.class);
        Assert.assertThat(listOfOrders.getOrders(), Matchers.not(Matchers.empty()));
    }
}
