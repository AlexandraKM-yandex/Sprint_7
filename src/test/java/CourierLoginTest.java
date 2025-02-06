import courier.CourierCreateSteps;
import courier.CourierLoginSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import org.junit.Before;
import org.junit.Test;

public class CourierLoginTest {

    private final String login = "login2025";
    private final String password = "2025";
    private final String firstName = "Name2025";

    @Before
    public void setUp() {
        Base.baseUrl();
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Проверяем, что при авторизации значение id не пустое и код ответа  200")
    public void loginCourier() {
        CourierCreateSteps.createCourier(login, password, firstName);
        Response response = CourierLoginSteps.loginCourierResponse(login, password);
        CourierLoginSteps.responseAfterSuccessfulAuthorization(response);;
        String id = response.jsonPath().getString("id");
        CourierCreateSteps.deleteCourier(id);
    }

    @Test
    @DisplayName("Появление ошибки при неверном логине")
    @Description("Проверка, что при авторизации с неверным логином значение message равно 'Учетная запись не найдена' и код ответа равен 404")
    public void cantAuthorizationWhenLoginIncorrect() {
        Response response = CourierLoginSteps.loginCourierResponse(login + "a", password);
        CourierLoginSteps.errorWhenLoginOrPasswordIncorrect(response);
    }

    @Test
    @DisplayName("Появление ошибки при неверном пароле")
    @Description("Проверка, что при авторизации с неверным паролем значение message равно 'Учетная запись не найдена' и код ответа равен 404")
    public void cantAuthorizationWhenPasswordIncorrect() {
        Response response = CourierLoginSteps.loginCourierResponse(login, password + 3);
        CourierLoginSteps.errorWhenLoginOrPasswordIncorrect(response);
    }

    @Test
    @DisplayName("Появление ошибки, если оставить логин пустыми при авторизации")
    @Description("Проверка, что при авторизации с пустым логином значение message равно 'Недостаточно данных для входа' и код ответа равен 400")
    public void cantAuthorizationWhenLoginIsEmpty() {
        Response response = CourierLoginSteps.loginCourierResponse("", password);
        CourierLoginSteps.errorWhenLoginPasswordEmpty(response);

    }

    @Test
    @DisplayName("Появление ошибки, если оставить пароль пустыми при авторизации")
    @Description("Проверка, что при авторизации с пустым паролем значение message равно 'Недостаточно данных для входа' и код ответа равен 400")
    public void cantAuthorizationWhenPasswordIsEmpty() {
        Response response = CourierLoginSteps.loginCourierResponse(login, "");
        CourierLoginSteps.errorWhenLoginPasswordEmpty(response);
    }
}