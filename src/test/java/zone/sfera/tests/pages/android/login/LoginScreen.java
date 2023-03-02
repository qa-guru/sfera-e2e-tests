package zone.sfera.tests.pages.android.login;

import zone.sfera.tests.pages.BaseScreen;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;

public class LoginScreen extends BaseScreen<LoginScreen> {

    @Step("Нажать на кнопку [SIGN IN WITH GOOGLE]")
    public void clickSignInWithGoogleButton() {
        elementBySferaId("tvSignWithGoogle").click();
    }

    @Step("Нажать на кнопку [SIGN IN WITH APPLE]")
    public void clickSignInWithAppleButton() {
        elementBySferaId("btnAppleAuth").click();
    }

    @Step("Нажать на кнопку [SIGN IN WITH SFERA ID]")
    public SignInWithSferaIdScreen clickSignInWithSferaIdButton() {
        elementBySferaId("vEnter").click();
        return new SignInWithSferaIdScreen();
    }

    @Step("Проверить, что экран авторизации успешно загружен")
    public LoginScreen checkBeLoaded() {
        elementBySferaId("vEnter").shouldBe(visible);
        return this;
    }

}
