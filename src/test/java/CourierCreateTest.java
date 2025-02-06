import courier.CourierCreateSteps;
import courier.CourierLoginSteps;
import courier.CourierLoginSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;


import org.junit.Before;
import org.junit.Test;


public class CourierCreateTest {

    private final String login = "login2025";
    private final String password = "2025";
    private final String firstName = "name2025";

    @Before
    public void setUp() {
        Base.baseUrl();
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверка, что возвращаемые тело ответа 'ok: true' и код ответа '201'")
    public void createNewCourier() {
        Response response = CourierCreateSteps.createCourier(login, password, firstName);
        CourierCreateSteps.checkCreatingCourier(response); // Проверка успешного создания курьера
        CourierCreateSteps.deleteCourier(CourierLoginSteps.loginCourierId(login, password)); // Удаление курьера после теста
    }

    @Test
    @DisplayName("Создание курьера с заполнением только обязательных полей")
    @Description("Проверка, что возвращаемые тело ответа 'ok: true' и код ответа '201'")
    public void createCourierWithOnlyRequiredFields() {
        Response response = CourierCreateSteps.createCourierWithoutFirstName(login, password);
        CourierCreateSteps.checkCreatingCourier(response); // Проверка успешного создания курьера
        CourierCreateSteps.deleteCourier(CourierLoginSteps.loginCourierId(login, password)); // Удаление курьера после теста
    }

    @Test
    @DisplayName("Невозможность создать курьера, логин которого уже есть в базе данных")
    @Description("Проверка, что возвращаемые тело ответа 'message: Этот логин уже используется. Попробуйте другой' и код ответа '409'")
    public void cantCreateDuplicateCourier() {
        CourierCreateSteps.createCourier(login, password, firstName);
        Response response = CourierCreateSteps.createCourier(login, password, firstName);
        CourierCreateSteps.checkCreatingCourierWithDuplicatedLogin(response); // Проверка ошибки с дублированным логином
        CourierCreateSteps.deleteCourier(CourierLoginSteps.loginCourierId(login, password)); // Удаление курьера после теста
    }

    @Test
    @DisplayName("Невозможность создать курьера, если не ввести логин или пароль")
    @Description("Проверка, что возвращаемые тело ответа 'message: Недостаточно данных для создания учетной записи' и код ответа '400'")
    public void checkResponseWithoutLogin() {
        Response response = CourierCreateSteps.createCourier("", password, firstName);
        CourierCreateSteps.checkCreatingCourierWithoutLoginPassword(response); // Проверка ошибки при отсутствии логина
    }

    @Test
    @DisplayName("Невозможность создать курьера, если не ввести логин или пароль")
    @Description("Проверка, что возвращаемые тело ответа 'message: Недостаточно данных для создания учетной записи' и код ответа '400'")
    public void checkResponseWithoutPassword() {
        Response response = CourierCreateSteps.createCourier(login, "", firstName);
        CourierCreateSteps.checkCreatingCourierWithoutLoginPassword(response); // Проверка ошибки при отсутствии пароля
    }
}