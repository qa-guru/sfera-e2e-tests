package zone.sfera.tests.tests.android.settings;

import zone.sfera.tests.allure.AllureFeature;
import zone.sfera.tests.allure.AllureOwner;
import zone.sfera.tests.allure.AllureTag;
import zone.sfera.tests.annotation.Id;
import zone.sfera.tests.api.profile.information.ProfileInformationApi;
import zone.sfera.tests.api.profile.information.dto.UserProfileDto;
import zone.sfera.tests.tests.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Owner(AllureOwner.DIKAYA)
@Feature(AllureFeature.SETTINGS)        @Tag(AllureTag.ANDROID)
@Story("Проверка настроек")             @Tag(AllureTag.SETTINGS)
class SettingsTest extends BaseTest {

    @Test
    @Id(1011)
    @DisplayName("Личные данные - смена никнейма")
    void changeNickname() {
        String newNickname = faker.hobbit().character();

        successLoginBySferaId()
                .openMenu()
                .openSettings()
                .setNickname(newNickname);

        step("Проверить корректность отображения ник-нейма пользователя", () -> {
            sleep(2000);
            UserProfileDto currentProfile = ProfileInformationApi.getCurrentProfile();
            assertEquals(newNickname, currentProfile.getNickName());
        });
    }

    static Stream<Arguments> changeLanguageDataProvider() {
        return Stream.of(
                Arguments.of("Русский", "Настройки"),
                Arguments.of("Český", "Nastavení"),
                Arguments.of("English", "Settings")
        );
    }

    @Id(1012)
    @MethodSource("changeLanguageDataProvider")
    @DisplayName("Смена языка на:")
    @ParameterizedTest(name = "{0}")
    void changeLanguage(String language, String expectedTextInSettingsHeader) {
        successLoginBySferaId()
                .openMenu()
                .openSettings();
        settingsScreen.setLanguage(language)
                .checkHeaderContainsText(expectedTextInSettingsHeader);
    }

    @Test
    @Id(1013)
    @DisplayName("Условия и политика - открывается для прочтения")
    void openTermsAndPolicies() {
        successLoginBySferaId()
                .openMenu()
                .openSettings();
        settingsScreen.loadTermsAndPolicy();
    }

    @Test
    @Id(1014)
    @DisplayName("Задать вопрос - написать и отправить сообщение")
    void sendMessageToSupport() {
        String message = faker.howIMetYourMother().quote();

        successLoginBySferaId()
                .openMenu()
                .openSettings();
        settingsScreen.sendMessageToSupport(message);
    }

    @Test
    @Id(1015)
    @DisplayName("Безопасность - смена пароля")
    @Disabled("Сложная логика. Отрефакторить тест чуть позже (Kireev)")
    void changePassword() {
//        String newPassword = "888888";
//
//        step("Авторизация", () -> {
//            loginPage
//                    .clickEnter()
//                    .setLogin(Credentials.sferaId.login())
//                    .setPassword(Credentials.sferaId.password())
//                    .clickEnter();
//            mainPage
//                    .shouldHaveTitle("Main section");
//        });
//
//        step("Смена пароля", () -> {
//            settingsPage
//                    .clickMenuButton()
//                    .clickSettingsButton()
//                    .clickSecurityButton()
//                    .changePassword(Credentials.sferaId.password(), newPassword);
//        });
//
//        step("Разлогинивание", () -> {
//            settingsPage
//                    .clickBackButton()
//                    .clickBackButton()
//                    .clickMenuButton()
//                    .clickQuitButton()
//                    .clicklogoutButton();
//        });
//
//        step("Авторизация c новым паролем", () -> {
//            loginPage
//                    .clickEnter()
//                    .setLogin(Credentials.sferaId.login())
//                    .setPassword(newPassword)
//                    .clickEnter();
//            mainPage
//                    .shouldHaveTitle("Main section");
//        });
//
//        step("Шаги после. Смена пароля", () -> {
//            settingsPage
//                    .clickMenuButton()
//                    .clickSettingsButton()
//                    .clickSecurityButton()
//                    .changePassword(newPassword, Credentials.sferaId.password());
//        });
    }

    @Disabled("баг - не сохраняются настройки тумблера")
    @Test
    @Id(1016)
    @DisplayName("Безопасность - вкл/выкл тумблер")
    void turnOnAskPasswordToggle() {
//        step("Авторизация", () -> {
//            loginPage
//                    .clickEnter()
//                    .setLogin(Credentials.sferaId.login())
//                    .setPassword(Credentials.sferaId.password())
//                    .clickEnter();
//            mainPage
//                    .shouldHaveTitle("Main section");
//        });
//
//        step("Включить переключатель Always ask for password", () -> {
//            settingsPage
//                    .clickMenuButton()
//                    .clickSettingsButton()
//                    .clickSecurityButton()
//                    .turnOnToggle();
//        });
//
//        step("Убедится что переключатель Always ask for password включен", () -> {
//            settingsPage
//                    .clickBackButton()
//                    .clickSecurityButton()
//                    .checkToggleTurnOn();
//        });
//
//        step("Убедится что при открытии приложения требуется авторизация", () -> {
//            // TODO: 16.07.2022 переключатель не работает
//        });
    }

}
