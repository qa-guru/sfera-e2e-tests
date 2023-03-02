package zone.sfera.tests.pages.android;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import zone.sfera.tests.enums.ReportReason;
import zone.sfera.tests.pages.BaseScreen;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePartnerScreen extends BaseScreen<ProfilePartnerScreen> {

    private final SelenideElement subscribeButton = elementBySferaId("tvSubscribe");
    private final SelenideElement moreActionsButton = elementBySferaId("btnMoreActions");

    @Step("Подписаться на пользователя")
    public ProfilePartnerScreen subscribe() {
        subscribeButton.shouldHave(text("SUBSCRIBE")).click();
        subscribeButton.shouldHave(text("UNSUBSCRIBE"));
        return this;
    }

    @Step("Отписаться от пользователя")
    public ProfilePartnerScreen unsubscribe() {
        subscribeButton.shouldHave(text("UNSUBSCRIBE")).click();
        subscribeButton.shouldHave(text("SUBSCRIBE"));
        return this;
    }

    @Step("Скопировать ID пользователя (тап по ID в Header)")
    public ProfilePartnerScreen copyIdByTap() {
        elementBySferaId("tvId").click();
        return this;
    }

    @Step("Скопировать ID пользователя (через [Еще] - 'Copy ID')")
    public ProfilePartnerScreen copyByMoreButton() {
        moreActionsButton.click();
        elementBySferaId("btnCopyId").click();
        return this;
    }

    @Step("Открыть доп. меню [Еще] и закрыть его через кнопку [Cancel]")
    public ProfilePartnerScreen openMoreActionsMenuAndCloseIt() {
        moreActionsButton.click();
        elementBySferaId("btnCancel").click();
        elementBySferaId("btnCancel").shouldNotBe(visible);
        return this;
    }

    @Step("Пожаловаться на пользователя. Причина - [{reason}]")
    public ProfilePartnerScreen report(ReportReason reason) {
        moreActionsButton.click();
        elementBySferaId("btnComplain").click();
        elementBySferaId("rgComplaints").shouldBe(visible);
        chooseReportReason(reason);
        elementBySferaId("rgComplaints").shouldNotBe(visible);
        return this;
    }

    @Step("Выбрать причину для жалобы - [{reason}]")
    public ProfilePartnerScreen chooseReportReason(ReportReason reason) {
        $$(AppiumBy.className("android.widget.RadioButton"))
                .find(Condition.text(reason.toString()))
                .click();
        elementBySferaId("btnSend").click();
        return this;
    }

    @Step("Проверить, что информация в блоке [Обо мне] = [{expectedAboutMeInformation}]")
    public ProfilePartnerScreen checkAboutMeInformationIsEquals(String expectedAboutMeInformation) {
        elementBySferaId("etvAboutMe").shouldHave(text(expectedAboutMeInformation));
        return this;
    }

    @Step("Пожаловаться на аватар. Причина - [{reason}]")
    public ProfilePartnerScreen reportIssueAvatar(ReportReason reason) {
        elementBySferaId("ivAvatar").click();
        elementBySferaId("ivSettings").click();
        elementBySferaId("tvComplaint").click();
        elementBySferaId("llBackground").shouldBe(visible);
        chooseReportReason(reason);
        elementBySferaId("llBackground").shouldNotBe(visible);
        return this;
    }

    @Step("Пожаловаться на фото. Причина - [{reason}]")
    public ProfilePartnerScreen reportIssuePhoto(ReportReason reason) {
        elementBySferaId("rvPhotos").shouldBe(visible);
        collectionBySferaId("ivPhoto").get(0).click();
        elementBySferaId("ivSettings").click();
        elementBySferaId("tvComplaint").click();
        elementBySferaId("llBackground").shouldBe(visible);
        chooseReportReason(reason);
        elementBySferaId("llBackground").shouldNotBe(visible);
        return this;
    }

    @Step("Пожаловаться на видео. Причина - [{reason}]")
    public ProfilePartnerScreen reportIssueVideo(ReportReason reason) {
        elementBySferaId("ivMore").click();
        elementBySferaId("tvDelete").click();
        elementBySferaId("llBackground").shouldBe(visible);
        chooseReportReason(reason);
        elementBySferaId("llBackground").shouldNotBe(visible);
        return this;
    }
}
