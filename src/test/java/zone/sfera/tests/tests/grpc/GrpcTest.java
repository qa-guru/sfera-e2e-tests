package zone.sfera.tests.tests.grpc;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zona.sfera.ui.MessangerProto;
import zone.sfera.tests.allure.AllureOwner;
import zone.sfera.tests.allure.AllureTag;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Owner(AllureOwner.VASENKOV)
//@Feature(AllureFeature.SETTINGS)
@Story("Проверка работы GRPC")
@Tag(AllureTag.GRPC)
public class GrpcTest extends BaseGrpcTest {

    private static final Logger log = LoggerFactory.getLogger(GrpcTest.class);

    @Test
    @DisplayName("Отправка двух сообщений через Stream, проверка, что в ответе те же сообщения")
    void sendMessageWithStreamResponseShouldContainSameMessageTest() throws Exception {
        String uniqueOIDOne = UUID.randomUUID().toString();
        String uniqueOIDTwo = UUID.randomUUID().toString();
        long fromId = 121153373;
        long recipientOne = 121153372;
        long recipientTwo = 121153374;
        long chatId = 3564;
        String textForFirstMessage = "1st test message";
        String textForSecondMessage = "2nd test message";

        Queue<MessangerProto.StreamRequest> defaultReq = new LinkedList<>() {{
            add(MessangerProto.StreamRequest.newBuilder()
                    .setMessage(
                            MessangerProto.Message.newBuilder()
                                    .setType(MessangerProto.MessageTypes.TEXT)
                                    .setFrom(fromId)
                                    .setTo(chatId)
                                    .setText(textForFirstMessage)
                                    .setOID(uniqueOIDOne)
                                    .build()
                    )
                    .build());
            add(MessangerProto.StreamRequest.newBuilder()
                    .setMessage(
                            MessangerProto.Message.newBuilder()
                                    .setType(MessangerProto.MessageTypes.TEXT)
                                    .setFrom(fromId)
                                    .setTo(chatId)
                                    .setText(textForSecondMessage)
                                    .setOID(uniqueOIDTwo)
                                    .build()
                    )
                    .build());
        }};

        Map<String, MessangerProto.StreamResponse> responseMap = sendMessagesWithStream(defaultReq, log);

        assertTrue(responseMap.containsKey(uniqueOIDOne));
        assertTrue(responseMap.containsKey(uniqueOIDTwo));
        assertEquals(2, responseMap.size());

        MessangerProto.StreamResponse responseOne = responseMap.get(uniqueOIDOne);
        MessangerProto.Message messageInResponseOne = responseOne.getMessage();
        MessangerProto.StreamResponse responseTwo = responseMap.get(uniqueOIDTwo);
        MessangerProto.Message messageInResponseOTwo = responseTwo.getMessage();

        assertFalse(responseOne.hasError());
        assertFalse(responseTwo.hasError());
        assertEquals(fromId, messageInResponseOne.getFrom());
        assertEquals(chatId, messageInResponseOne.getTo());
        assertEquals(textForFirstMessage, messageInResponseOne.getText());
        assertEquals(uniqueOIDOne, messageInResponseOne.getOID());
        assertEquals(3, messageInResponseOne.getRecipientList().size());
        assertTrue(messageInResponseOne.getRecipientList().contains(fromId));
        assertTrue(messageInResponseOne.getRecipientList().contains(recipientOne));
        assertTrue(messageInResponseOne.getRecipientList().contains(recipientTwo));
        assertEquals(fromId, messageInResponseOTwo.getFrom());
        assertEquals(chatId, messageInResponseOTwo.getTo());
        assertEquals(textForSecondMessage, messageInResponseOTwo.getText());
        assertEquals(uniqueOIDTwo, messageInResponseOTwo.getOID());
        assertEquals(3, messageInResponseOTwo.getRecipientList().size());
        assertTrue(messageInResponseOTwo.getRecipientList().contains(fromId));
        assertTrue(messageInResponseOTwo.getRecipientList().contains(recipientOne));
        assertTrue(messageInResponseOTwo.getRecipientList().contains(recipientTwo));
    }

    @Test
    @DisplayName("Отправка сообщения с неправильным chatId через Stream, проверка, что в ответе указана ошибка")
    void sendMessageWithStreamWithWrongChatIdResponseShouldContainErrorTest() throws Exception {
        String uniqueOID = UUID.randomUUID().toString();
        long fromId = 121153373;
        long chatId = Integer.MAX_VALUE;
        String text = "test message";

        Queue<MessangerProto.StreamRequest> defaultReq = new LinkedList<>() {{
            add(MessangerProto.StreamRequest.newBuilder()
                    .setMessage(
                            MessangerProto.Message.newBuilder()
                                    .setType(MessangerProto.MessageTypes.TEXT)
                                    .setFrom(fromId)
                                    .setTo(chatId)
                                    .setText(text)
                                    .setOID(uniqueOID)
                                    .build()
                    )
                    .build());
        }};

        Map<String, MessangerProto.StreamResponse> responseMap = sendMessagesWithStream(defaultReq, log);
        MessangerProto.StreamResponse response = responseMap.get(uniqueOID);

        assertTrue(response.hasError());
        assertEquals("chat not found", response.getError().getMsg());

    }

    @Test
    @DisplayName("Проверка sync с указанием lastMessageId возвращает нужные сообщения")
    void syncWithLastMessageIdShouldReturnCorrectMessagesTest() throws Exception {
        String uniqueOIDOne = UUID.randomUUID().toString();
        String uniqueOIDTwo = UUID.randomUUID().toString();
        long fromId = 121153373;
        long recipientOne = 121153372;
        long recipientTwo = 121153374;
        long chatId = 3564;
        String textForFirstMessage = "1st test message";
        String textForSecondMessage = "2nd test message";

        Queue<MessangerProto.StreamRequest> defaultReq = new LinkedList<>() {{
            add(MessangerProto.StreamRequest.newBuilder()
                    .setMessage(
                            MessangerProto.Message.newBuilder()
                                    .setType(MessangerProto.MessageTypes.TEXT)
                                    .setFrom(fromId)
                                    .setTo(chatId)
                                    .setText(textForFirstMessage)
                                    .setOID(uniqueOIDOne)
                                    .build()
                    )
                    .build());
            add(MessangerProto.StreamRequest.newBuilder()
                    .setMessage(
                            MessangerProto.Message.newBuilder()
                                    .setType(MessangerProto.MessageTypes.TEXT)
                                    .setFrom(fromId)
                                    .setTo(chatId)
                                    .setText(textForSecondMessage)
                                    .setOID(uniqueOIDTwo)
                                    .build()
                    )
                    .build());
        }};

        Map<String, MessangerProto.StreamResponse> responseMap = sendMessagesWithStream(defaultReq, log);

        assertTrue(responseMap.containsKey(uniqueOIDOne));
        assertTrue(responseMap.containsKey(uniqueOIDTwo));

        String messageIdOne = responseMap.get(uniqueOIDOne).getMessage().getId();
        String messageIdTwo = responseMap.get(uniqueOIDTwo).getMessage().getId();

        MessangerProto.SyncRequest syncRequest = MessangerProto.SyncRequest.newBuilder()
                .setLastMessageUuid(messageIdOne)
                .build();
        Iterator<MessangerProto.Message> syncResponse = chatBlockingStub.sync(syncRequest);
        List<MessangerProto.Message> syncResponseList = IteratorUtils.toList(syncResponse);

        assertEquals(1, syncResponseList.size());

        MessangerProto.Message messageFromSync = syncResponseList.get(0);
        assertEquals(fromId, messageFromSync.getFrom());
        assertEquals(chatId, messageFromSync.getTo());
        assertEquals(textForSecondMessage, messageFromSync.getText());
        assertEquals(uniqueOIDTwo, messageFromSync.getOID());
        assertEquals(messageIdTwo, messageFromSync.getId());
        assertEquals(3, messageFromSync.getRecipientList().size());
        assertTrue(messageFromSync.getRecipientList().contains(fromId));
        assertTrue(messageFromSync.getRecipientList().contains(recipientOne));
        assertTrue(messageFromSync.getRecipientList().contains(recipientTwo));

        syncRequest = MessangerProto.SyncRequest.newBuilder()
                .setLastMessageUuid(messageIdTwo)
                .build();
        syncResponse = chatBlockingStub.sync(syncRequest);
        syncResponseList.clear();
        syncResponseList = IteratorUtils.toList(syncResponse);

        assertEquals(0, syncResponseList.size());
    }

    @ParameterizedTest
    @CsvSource({"9,10", "10,10", "13,13", "100,100", "101,10"})
    @DisplayName("Проверка sync с указанием limit возвращает нужное количество сообщений")
    void syncWithLimitShouldReturnCorrectAmountOfMessagesTest(int limit, int expectedSize) {
        MessangerProto.SyncRequest syncRequest = MessangerProto.SyncRequest.newBuilder()
                .setLimit(limit)
                .build();
        Iterator<MessangerProto.Message> syncResponse = chatBlockingStub.sync(syncRequest);
        List<MessangerProto.Message> syncResponseList = IteratorUtils.toList(syncResponse);

        assertEquals(expectedSize, syncResponseList.size());
    }

    @ParameterizedTest
    @CsvSource({"9,10", "10,10", "13,13", "100,100", "101,10"})
    @DisplayName("Проверка syncSingleChatById с указанием chatId и limit возвращает нужное количество сообщений")
    void syncSingleChatByIdWithLimitShouldReturnCorrectAmountOfMessagesTest(int limit, int expectedSize) {
        MessangerProto.SRChatMessages syncRequest = MessangerProto.SRChatMessages.newBuilder()
                .setChatId(3564)
                .setLimit(limit)
                .build();
        Iterator<MessangerProto.Message> syncResponse = chatBlockingStub.syncSingleChatById(syncRequest);
        List<MessangerProto.Message> syncResponseList = IteratorUtils.toList(syncResponse);

        assertEquals(expectedSize, syncResponseList.size());
    }

    @Test
    @DisplayName("Проверка syncSingleChatById с указанием chatId возвращает сообщения из нужного чата")
    void syncSingleChatByIdWithChatIdShouldReturnMessagesFromCorrectChatTest() {
        MessangerProto.SRChatMessages syncRequest = MessangerProto.SRChatMessages.newBuilder()
                .setChatId(3564)
                .setLimit(10)
                .build();
        Iterator<MessangerProto.Message> syncResponse = chatBlockingStub.syncSingleChatById(syncRequest);

        while (syncResponse.hasNext()) {
            assertEquals(3564, syncResponse.next().getChatId());
        }
    }
}