package courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginSteps {

    @Step("Авторизация")
    public static String loginCourierId(String login, String password) {
        CourierLogin courierLogin = new CourierLogin(login, password);
        Response responseOfLoginCourier =
                given()
                        .contentType(ContentType.JSON)
                        .body(courierLogin)
                        .when()
                        .post("/api/v1/courier/login");
        responseOfLoginCourier.then().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());

        return responseOfLoginCourier.jsonPath().getString("id");
    }

    @Step("Авторизация курьера на сайте")
    public static Response loginCourierResponse(String login, String password) {
        CourierLogin courierLogin = new CourierLogin(login, password);
        return given()
                .contentType(ContentType.JSON)
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("После успешной авторизации получаем id и код ответа 200")
    public static void responseAfterSuccessfulAuthorization(Response response) {
        response.then()
                .assertThat()
                .body("id", notNullValue())
                .statusCode(200);
    }

    @Step("Ошибка и статус 404 при некорректном логине/пароле")
    public static void errorWhenLoginOrPasswordIncorrect(Response response) {
        response.then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Step("Ошибка и статус 400 при пустых полях логин/пароль")
    public static void errorWhenLoginPasswordEmpty(Response response) {
        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }
}
