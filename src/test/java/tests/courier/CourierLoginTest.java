package tests.courier;

import api.client.CourierClient;
import api.model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class CourierLoginTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Вход курьера в систему")
    @Description("Проверка успешного входа курьера в систему")
    public void authorizationTest(){
        CourierClient courierClient = new CourierClient();
        Courier courier = new Courier();

        courier.setLogin("naruto7777");
        courier.setPassword("12345");
        Response response2 = courierClient.sendPostRequestApiV1CourierCreate(courier);

        Response response = courierClient.sendPostRequestApiV1CourierLogin(courier);
        response.then()
                .log().all()
                .assertThat().body("id", Matchers.notNullValue()).and().statusCode(200);

    }

    @Test
    @DisplayName("Вход курьера в систему без логина")
    @Description("Проверка входа курьера в систему без логина (код 400)")
    public void authorizationWithoutLoginTest(){
        CourierClient courierClient = new CourierClient();

        Courier courier = new Courier();
        courier.setPassword("12345");
        Response response = courierClient.sendPostRequestApiV1CourierLogin(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.is("Недостаточно данных для входа")).
                and().statusCode(400);

    }

    @Test
    @DisplayName("Вход курьера в систему без пароля")
    @Description("Проверка входа курьера в систему без пароля (код 400)")
    public void authorizationWithoutPasswordTest(){
        CourierClient courierClient = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("naruto7777");
        courier.setPassword("");
        Response response = courierClient.sendPostRequestApiV1CourierLogin(courier);
        response.then()
                .log().all()
                .assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Вход курьера в систему с неверным паролем")
    @Description("Проверка входа курьера в систему с неправильным паролем (код 404)")
    public void authorizationWithWrongPasswordTest(){
        CourierClient courierClient = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("naruto7777");
        courier.setPassword("1234");
        Response response = courierClient.sendPostRequestApiV1CourierLogin(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Вход курьера в систему с неверным логином")
    @Description("Проверка входа курьера в систему с неверным логином (код 404)")
    public void authorizationWithWrongLoginTest(){
        CourierClient courierClient = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("Naruto666");
        courier.setPassword("12345");
        Response response = courierClient.sendPostRequestApiV1CourierLogin(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Вход курьера в систему с неверными логином и паролем")
    @Description("Проверка входа курьера в систему с неверным логином и паролем (код 404)")
    public void authorizationWithWrongLoginAndPasswordTest(){
        CourierClient courierClient = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("UchihaSasuke");
        courier.setPassword("Amaterasu");
        Response response = courierClient.sendPostRequestApiV1CourierLogin(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);
    }
}

