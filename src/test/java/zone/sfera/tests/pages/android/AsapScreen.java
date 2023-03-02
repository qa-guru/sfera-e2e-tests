package zone.sfera.tests.pages.android;

import com.codeborne.selenide.Condition;
import zone.sfera.tests.pages.BaseScreen;
import zone.sfera.tests.pages.android.menu.MenuScreen;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class AsapScreen extends BaseScreen<AsapScreen> {

    @Step("Открыть меню")
    public MenuScreen openMenu() {
        elementBySferaId("ivNavDrawer").click();
        return new MenuScreen();
    }

    @Step("Открыть чат с пользователем [{userNickname}]")
    public ChatScreen openChatWith(String userNickname) {
        elementBySferaId("tvAsapUserName").shouldBe(Condition.visible);
        collectionBySferaId("tvAsapUserName").find(Condition.text(userNickname)).click();
        return new ChatScreen();
    }

    @Step("Создать чат с пользователем с id [{partnerProfileId}]")
    public ChatScreen createChat(int partnerProfileId) {
        elementBySferaId("fabPlus").click();
        elementBySferaId("tvTitleToolbar").shouldHave(text("Create chat"));
        elementBySferaId("ivSearch").click();
        elementBySferaId("etSearch").sendKeys(String.valueOf(partnerProfileId));
        elementBySferaId("tvId").shouldHave(text(String.valueOf(partnerProfileId))).click();
        elementBySferaId("fabNext").click();
        return new ChatScreen();
    }

    @Step("Проверить, что экран [ASAP] успешно загружен")
    public AsapScreen checkBeLoaded() {
        elementBySferaId("tvCategoryTitle").shouldBe(visible);
        return this;
    }

}
