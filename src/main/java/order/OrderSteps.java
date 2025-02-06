package order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderSteps {

    @Step("Успешное создание заказа")
    public static Response OrderCreate(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        OrderCreate OrderCreate = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return given()
                .contentType(ContentType.JSON)
                .body(OrderCreate).when()
                .post("/api/v1/orders");
    }

    @Step("Код ответа 201 и наличие 'track' в ответе при успешном создании заказа")
    public static void orderCreationSuccess(Response response) {
        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Step("Получение списка заказов")
    public static Response getOrdersList() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/orders?courierId=");
    }

    @Step("Код ответа 200 и список заказов не пустой")
    public static void checkResponseAndStatusCodeWhenGetOrderList(Response response) {
        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
    }

}
