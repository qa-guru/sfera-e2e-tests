package zone.sfera.tests.tests.ios;

import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.tests.BaseTest;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

@Tag("ios")
public class LoginTest extends BaseTest {

    @Test
    @Owner("ElakovNick")
    @DisplayName("Successful login with sferaId")
    void successfulLoginWithSferaIdTest() {
        switchTo().alert().accept();
        switchTo().alert().accept();
        $(AppiumBy.name("Continue with SFERA ID")).click();
        $(AppiumBy.xpath("//*[@value='SFERA ID']")).sendKeys(Credentials.sferaId.login());
        $(AppiumBy.xpath("//*[@value='Password']")).sendKeys(Credentials.sferaId.password());
        $(AppiumBy.name("Sign In")).click();

//        $(AppiumBy.name("Main section")).click(); // search for name don't work and for xPath too and accessibilityId too
        // TODO: add check list in the pop up after tap on the Main section
    }

}
