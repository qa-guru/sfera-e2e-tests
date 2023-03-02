package zone.sfera.tests.tests.android.login;

import zone.sfera.tests.allure.AllureFeature;
import zone.sfera.tests.allure.AllureOwner;
import zone.sfera.tests.allure.AllureTag;
import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.tests.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Owner(AllureOwner.AMIDOSHA)
@Feature(AllureFeature.LOGIN)           @Tag(AllureTag.ANDROID)
@Story("Авторизация через sfera id")    @Tag(AllureTag.LOGIN)
public class LoginBySferaIdTest extends BaseTest {

    @Test
    @Owner(AllureOwner.AMIDOSHA)
    @DisplayName("Успешная авторизация")
    void successfulLogin() {
        loginScreen.clickSignInWithSferaIdButton()
                .signInWithDefaultCreds();
        asapScreen.checkBeLoaded();
    }

    @Test
    @Owner(AllureOwner.AMIDOSHA)
    @DisplayName("Авторизация: неверный пароль")
    void loginWithInvalidPassword() {
        String validLogin = Credentials.sferaId.login();
        String invalidPassword = "aa-bb-cc-dd";

        loginScreen.clickSignInWithSferaIdButton()
                .signIn(validLogin, invalidPassword)
                .checkDisplayInvalidPasswordAlert();
    }

    @Test
    @Disabled
    @DisplayName("Проверка компонента поля ввода пароля")
    void passwordComponentTest() {
//        Selenide.clipboard().setText("123456");
//        loginPage
//                .clickEnter()
//                .setLogin(Credentials.sferaId.login())
//                .setPassword("123456")
//                .clickEnter()
//                .shouldHaveErrorText("You entered an incorrect ID or password, please try again");
    }

    @Test
    @Disabled("Enable capabilities with permissions")
    @DisplayName("Успешная авторизация без выданных разрешений (permissions)")
    void withoutLoginPermissions() {
//        loginPage.clickEnter()
//                .setLogin(Credentials.sferaId.login())
//                .setPassword(Credentials.sferaId.password())
//                .clickEnter();
//        permissionsComponent
//                .grantDialog()
//                .permissionMessage()
//                .permissionAllowForegroundOnlyButton()
//                .permissionMessageAudio()
//                .permissionAllowButton();
//        mainPage
//                .shouldHaveTitle("Primary");
    }

}
