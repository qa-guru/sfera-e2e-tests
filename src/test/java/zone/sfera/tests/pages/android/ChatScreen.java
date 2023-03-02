package zone.sfera.tests.pages.android;

import zone.sfera.tests.pages.BaseScreen;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.itemWithText;

public class ChatScreen extends BaseScreen<ChatScreen> {

    @Step("Открыть информацию о партнере")
    public AboutPartnerScreen openAboutPartnerScreen() {
        elementBySferaId("ivChatAvatar").click();
        return new AboutPartnerScreen();
    }

    @Step("Отправить сообщение [{message}]")
    public ChatScreen sendMessage(String message) {
        elementBySferaId("etSendMessage").sendKeys(message);
        elementBySferaId("ivSend").click();
        return this;
    }

    @Step("Проверить, что среди отправленных сообщений есть [{expectedMessage}]")
    public ChatScreen checkSendedMessagesContain(String expectedMessage) {
        collectionBySferaId("tvSendMessage").shouldHave(itemWithText(expectedMessage));
        return this;
    }

}
