package tests.order;

import api.model.Colors;
import api.model.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private Order order;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new AllureRestAssured());
    }

    @Parameterized.Parameters
    public static Object[][] getOrderParameters() {
        return new Object[][] {
                {new Order("Kakashi", "Hatake", "Konoha, 17 apt.", "3",
                        "8-800-555-35-35", "2023-11-17", "Copy ninja, pervert",
                        List.of(Colors.GRAY.name()), 10)},
                {new Order("Naruto", "Uzumaki", "Konoha, 142 apt.", "4",
                        "+7-800-355-35-35", "2023-07-07",
                        "I'm gonna become a Hokage. Believe it!", List.of(Colors.BLACK.name()), 6)},
                {new Order("Hashirama", "Senju", "Konoha, 1 apt.", "5",
                        "+7-999-999-99-99", "2023-09-19", "God of shinobi",
                        List.of(Colors.GRAY.name(), Colors.BLACK.name()), 1)},
                {new Order("Minato", "Namikaze", "Konoha, 142 apt.", "4",
                        "+7-987-654-32-10", "2023-10-28",
                        "The Yellow Flash", Collections.emptyList(), 4)}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Успешное создание заказа с разными параметрами")
    public void createOrderTest() {
        Response response = RestAssured.given()
                .log().all()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
        response.then().log().all()
                .assertThat().body("track", Matchers.notNullValue()).and().statusCode(201);
    }
}
