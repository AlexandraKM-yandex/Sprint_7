package courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateSteps {

    @Step("Cоздаём курьера")
    public static Response createCourier(String login, String password, String firstName) {
        CourierCreate courierCreate = new CourierCreate(login, password, firstName);
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreate)
                .when()
                .post("/api/v1/courier");
    }


    @Step("Статус ответа: 201, поле 'ok': true" +
            "- при успешном создании")
    public static void checkCreatingCourier(Response response) {
        response.then()
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Step("Проверка создания курьера только с обязательными полями")
    public static Response createCourierWithoutFirstName(String login, String password) {
        CourierCreate courierCreate = new CourierCreate(login, password, "");
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreate)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Статус ответа: 400, поле 'message': 'Недостаточно данных для создания учетной записи'" +
            "- при создании учетной записи без логина/пароля")
    public static void checkCreatingCourierWithoutLoginPassword(Response response) {
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Статус ответа: 409, поле 'message': 'Этот логин уже используется. Попробуйте другой.'" +
            "- при создании учетной записи с дублирующимся логином")
    public static void checkCreatingCourierWithDuplicatedLogin(Response response) {
        response.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется."));
    }

    @Step("Удаление курьера")
    public static void deleteCourier(String id) {
        Response responseOfDeleteCourier =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .delete("/api/v1/courier/" + id);
        responseOfDeleteCourier.then().statusCode(200)
                .and()
                .assertThat().body("ok", equalTo(true));
    }
}

