import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BankLoginTest {

    @BeforeAll
    public static void setupAnd2factor() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserVersion", "125.0");
        capabilities.setCapability("selenoid:options", new HashMap<String, Object>() {{

            put("enableVNC", true);

            /* How to set session timeout */
            put("sessionTimeout", "4m");

            /* How to set timezone */
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});

            /* How to add "trash" button */
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});

            /* How to enable video recording */
            put("enableVideo", true);
        }});

        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.baseUrl = "https://idemo.bspb.ru";

        open("/");
        sleep(5000);
        $(By.name("username")).setValue("demo");
        sleep(5000);
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
        sleep(5000);
        $("#user-greeting").shouldHave(text("Hello World!"));
   }

    @Test
    public void badUserLoginToBank() {
        sleep(5000);
        $("#user-greeting").shouldHave(text("Hello Artem!"));
    }
}


