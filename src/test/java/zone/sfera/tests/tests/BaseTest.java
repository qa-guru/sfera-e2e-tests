package zone.sfera.tests.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.helpers.Attach;
import zone.sfera.tests.drivers.DriverSettings;
import zone.sfera.tests.pages.android.AsapScreen;
import zone.sfera.tests.pages.android.CropPhotoScreen;
import zone.sfera.tests.pages.android.ProfilePartnerScreen;
import zone.sfera.tests.pages.android.login.LoginScreen;
import zone.sfera.tests.pages.android.ProfileScreen;
import zone.sfera.tests.pages.android.settings.SettingsScreen;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static io.qameta.allure.Allure.step;

public class BaseTest {

    private RemoteWebDriver driver;

    // Locally = [mobile]; remotely = [browserstack]
    public static final String DEVICE_PROVIDER = System.getProperty("deviceProvider", "browserstack");

    protected final LoginScreen loginScreen = new LoginScreen();
    protected final ProfileScreen profileScreen = new ProfileScreen();
    protected final SettingsScreen settingsScreen = new SettingsScreen();
    protected final AsapScreen asapScreen = new AsapScreen();
    protected final ProfilePartnerScreen profilePartnerScreen = new ProfilePartnerScreen();
    protected final CropPhotoScreen cropPhotoScreen = new CropPhotoScreen();

    protected final Faker faker = new Faker();

    @BeforeAll
    static void setup() {
        Configuration.timeout = 60_000;
        addListener("AllureSelenide", new AllureSelenide());
        RestAssured.baseURI = Credentials.envConfig.baseApiURI();
    }

    @BeforeEach
    void startDriver() {
        driver = DriverSettings.getWebDriver(DEVICE_PROVIDER);
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterEach
    void closeDriverAndAddAttach() {
        String sessionId = driver.getSessionId().toString();
        Attach.screenshotAs("Last screenshot");

        step("Close driver", () -> driver.quit());

        if (DEVICE_PROVIDER.contains("browserstack"))
            Attach.video(sessionId);
    }

    @Step("Авторизоваться через Sfera ID")
    protected AsapScreen successLoginBySferaId() {
        loginScreen.clickSignInWithSferaIdButton()
                .signInWithDefaultCreds();
        return asapScreen.checkBeLoaded();
    }

}
