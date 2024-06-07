import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.step;

public class BankLoginTest extends BaseTest {

    @BeforeAll
    public static void setupAnd2factor()  {
        Configuration.remote = "http://localhost:4444/wd/hub";
        SelenideLogger.addListener("allure",new AllureSelenide());
        Configuration.baseUrl = "https://idemo.bspb.ru";

        step("Open auth page", () ->{
            open("/");
        });
        step("Set username demo", () ->{
            $(By.name("username")).setValue("demo");
        });
        step("Set password demo and click enter", () ->{
            $(By.name("password")).setValue("demo").pressEnter();
        });

        enter2ndFactor();

    }

    public static void enter2ndFactor() {
        step("Should be 2ndfactor window", () ->{
            $("#otp-code-text").shouldBe(visible);
        });
        step("Set sms code 0000 and enter confirm button", () -> {
            if ($("#login-crypto-button").isDisplayed()) {
                $("#login-crypto-button").click();
            }
            else {
                $(By.name("otpCode")).val("0000").pressEnter();
            }
        });

    }

    @Test
     void userLoginToBank() {

        $("#user-greeting").shouldHave(text("Hello World!"));
   }


}


