package tests.courier;

import api.client.CourierClient;
import api.model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    Integer id;


    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка что статус 201 после создания курьера")
    public void createCourierTest() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        Courier courier = new Courier(firstName, login, password);

        Response response = courierClient.sendPostRequestApiV1CourierCreate(courier);
        response.then()
                .log().all() // логироваание
                .assertThat().body("ok", Matchers.is(true)).and().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка сообщения о существовании курьера")
    public void createTwoIdenticalCouriersTest(){
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        Courier courier = new Courier(firstName, login, password);

        courierClient.sendPostRequestApiV1CourierCreate(courier);

        Response response = courierClient.sendPostRequestApiV1CourierCreate(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(HttpStatus.SC_CONFLICT);

        Response response2 = courierClient.sendPostRequestApiV1CourierLogin(courier);

        id = response2.then().extract().path("id");
    }

    @Test
    @DisplayName("Создание курьеров с одинаковыми логинами")
    @Description("Проверка сообщения о существовании курьера с введенным логином при попытке создания нового")
    public void createTwoIdenticalLoginTest(){
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        Courier courier = new Courier(firstName, login, password);

        RestAssured.given()
                .log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
        courier.setPassword("strongpass");
        Response response = courierClient.sendPostRequestApiV1CourierCreate(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Проверка сообщения о недостаточности данных для создания учетной записи")
    public void createCourierWithoutLoginAndPassword(){
        CourierClient courierClient = new CourierClient();
        String firstName = RandomStringUtils.randomAlphabetic(10);

        Courier courier = new Courier();
        courier.setFirstName(firstName);
        Response response = courierClient.sendPostRequestApiV1CourierCreate(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без логина и имени")
    @Description("Проверка сообщения о недостаточности данных для создания учетной записи")
    public void createCourierWithoutLoginAndFirstName(){
        CourierClient courierClient = new CourierClient();
        String password = RandomStringUtils.randomAlphabetic(10);

        Courier courier = new Courier();
        courier.setPassword(password);
        Response response = courierClient.sendPostRequestApiV1CourierCreate(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без пароля и имени")
    @Description("Проверка сообщения о недостаточности данных для создания учетной записи")
    public void createCourierWithoutPasswordAndFirstName(){
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphabetic(10);

        Courier courier = new Courier();
        courier.setLogin(login);
        Response response = courierClient.sendPostRequestApiV1CourierCreate(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка сообщения о недостаточности данных для создания учетной записи")
    public void createCourierWithoutPassword(){
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setFirstName(firstName);
        Response response = courierClient.sendPostRequestApiV1CourierCreate(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка сообщения о недостаточности данных для создания учетной записи")
    public void createCourierWithoutLogin(){
        CourierClient courierClient = new CourierClient();
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphanumeric(10);

        Courier courier = new Courier();
        courier.setFirstName(firstName);
        courier.setPassword(password);
        Response response = courierClient.sendPostRequestApiV1CourierCreate(courier);
        response.then()
                .log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @After
    public void tearDown() {
        if (id != null) {
            CourierClient courierClient = new CourierClient();
            courierClient.deleteCourierRequest(id);
        }
    }
}
