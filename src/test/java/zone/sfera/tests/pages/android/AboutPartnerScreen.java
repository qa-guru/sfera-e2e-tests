package zone.sfera.tests.pages.android;

import zone.sfera.tests.pages.BaseScreen;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;

public class AboutPartnerScreen extends BaseScreen<AboutPartnerScreen> {

    @Step("Проверить, что в профиле партнера никнейм равен [{expectedNickname}]")
    public AboutPartnerScreen checkNickname(String expectedNickname) {
        elementBySferaId("tvDataUsername").shouldHave(text(expectedNickname));
        return this;
    }

    @Step("Открыть экран профиля пользователя-партнера")
    public ProfilePartnerScreen openPartnerProfile() {
        elementBySferaId("ivInfoData").click();
        return new ProfilePartnerScreen();
    }

}
