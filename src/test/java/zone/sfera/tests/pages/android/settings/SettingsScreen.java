package zone.sfera.tests.pages.android.settings;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import zone.sfera.tests.pages.BaseScreen;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SettingsScreen extends BaseScreen<SettingsScreen> {

    @Step("Изменить никнейм на [{nickname}]")
    public SettingsScreen setNickname(String nickname) {
        elementBySferaId("tvPersonal").click();
        elementBySferaId("etNickname").sendKeys(nickname);
        elementBySferaId("ivDone").click();
        elementBySferaId("btnApply").click();
        return this;
    }

    @Step("Сменить язык приложения на [{language}]")
    public SettingsScreen setLanguage(String language) {
        elementBySferaId("tvLanguage").click();
        elementBySferaId("isRightMenu").shouldBe(visible);

        collectionBySferaId("tvLanguageTitle")
                .filter(Condition.text(language)).first()
                .click();
        elementBySferaId("btnChange").click();
        return this;
    }

    @Step("Загрузить условия и политику приложения")
    public void loadTermsAndPolicy() {
        elementBySferaId("tvPolitic").click();
        $x("//*[contains(@text, 'USER AGREEMENT and PRIVACY POLICY for SFERA software product')]")
                .shouldBe(visible);
    }

    @Step("Написать поддержке вопрос [{message}]")
    public void sendMessageToSupport(String message) {
        elementBySferaId("tvSupport").click();

        elementBySferaId("etSendMessage").sendKeys(message);
        elementBySferaId("ivSend").click();

        collectionBySferaId("tvSendMessage")
                .filter(Condition.text(message))
                .shouldHave(CollectionCondition.size(1));
    }

    @Step("Проверить, что Header настроек содержит текст [{text}]")
    public SettingsScreen checkHeaderContainsText(String text) {
        elementBySferaId("tvTitle").shouldHave(text(text));
        return this;
    }

}
