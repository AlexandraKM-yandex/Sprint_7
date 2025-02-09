import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.OrderSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class OrderTest {
    private final List<String> color;
    private int track;

    public OrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Collection<Object[]> colorParameters() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {Arrays.asList()}
        });
    }

    @Before
    public void setUp() {
        Base.baseUrl();
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с разными цветами самокатов " +
            "либо вообще без указанного цвета и проверка, что значение 'track' в ответе не пустое и код ответа 200")
    public void OrderCreate() {
        String firstName = "Alexandra";
        String lastName = "Belikova";
        String address = "Lenina, 5";
        int metroStation = 5;
        String phone = "+7 800 555 55 55";
        int rentTime = 1;
        String deliveryDate = "2025-05-05";
        String comment = "А вообще, я хотела розовый";
        Response response = OrderSteps.OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        track = response.jsonPath().getInt("track");
        OrderSteps.orderCreationSuccess(response);
    }

    @After
    public void cancel() {
        if (track != 0) {
            Response cancelResponse = OrderSteps.cancelOrder(track);
            OrderSteps.cancelOrderSuccess(cancelResponse);
        }
    }
}
