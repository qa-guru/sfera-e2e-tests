package zone.sfera.tests.pages.android.login;

import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.pages.BaseScreen;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;

public class SignInWithSferaIdScreen extends BaseScreen<SignInWithSferaIdScreen> {

    @Step("Войти под учетной записью sfera: логин = [{sferaId}], пароль = [{password}]")
    public SignInWithSferaIdScreen signIn(String sferaId, String password) {
        elementBySferaId("etId").sendKeys(sferaId);
        elementBySferaId("etPassword").sendKeys(password);
        elementBySferaId("vEnter").click();
        return this;
    }

    public void signInWithDefaultCreds() {
        String sferaId = Credentials.sferaId.login();
        String password = Credentials.sferaId.password();
        signIn(sferaId, password);
    }

    @Step("Проверить, что система отобразила ошибку несоответствия логина и пароля")
    public SignInWithSferaIdScreen checkDisplayInvalidPasswordAlert() {
        String errorMessage = "You entered an incorrect ID or password, please try again";
        elementBySferaId("tvError").shouldHave(text(errorMessage));
        return this;
    }

}
