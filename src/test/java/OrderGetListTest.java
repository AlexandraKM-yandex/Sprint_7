import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.OrderSteps;
import org.junit.Before;
import org.junit.Test;

public class OrderGetListTest {

    @Before
    public void setUp() {
        Base.baseUrl();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("При получении списка заказов значение 'orders' не пустое и код ответа равен 200")
    public void getOrderList() {
        Response response = OrderSteps.getOrdersList();
        OrderSteps.checkResponseAndStatusCodeWhenGetOrderList(response);
    }
}