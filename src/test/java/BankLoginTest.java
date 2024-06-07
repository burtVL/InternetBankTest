import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BankLoginTest extends BaseTest {

    @BeforeAll
    public static void setupAnd2factor()  {
        //Configuration.remote = "true";
        SelenideLogger.addListener("allure",new AllureSelenide());
        Configuration.baseUrl = "https://idemo.bspb.ru";

        open("/");
        $(By.name("username")).setValue("demo");
        $(By.name("password")).setValue("demo").pressEnter();
        enter2ndFactor();

    }

    public static void enter2ndFactor() {
        $("#otp-code-text").shouldBe(visible);
        if ($("#login-crypto-button").isDisplayed()) {
            $("#login-crypto-button").click();
        }
        else {
            $(By.name("otpCode")).val("0000").pressEnter();
        }
    }

    @Test
    public void userLoginToBank() {

        $("#user-greeting").shouldHave(text("Hello World!"));
   }


}


