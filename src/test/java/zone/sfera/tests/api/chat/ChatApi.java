package zone.sfera.tests.api.chat;

import zone.sfera.tests.api.base.BaseApi;
import zone.sfera.tests.api.chat.dto.createChat.CreateChatRequestDto;
import zone.sfera.tests.api.chat.dto.getListChats.ChatInformationDto;
import zone.sfera.tests.api.chat.dto.getListChats.GetListChatsResponseDto;
import io.qameta.allure.Step;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ChatApi extends BaseApi {

    @Step("Вызов API: получить список чатов пользователя")
    public static GetListChatsResponseDto getListChats() {
        return given().spec(defaultRequestSpecWithToken())
                    .queryParam("limit", 50)
                .when()
                    .get(ChatEndpoint.GET_LIST_CHATS)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200)
                    .extract()
                    .as(GetListChatsResponseDto.class);
    }

    @Step("Вызов API: создать новый чат")
    public static ChatInformationDto createChat(CreateChatRequestDto createChatRequestDto) {
        return given().spec(defaultRequestSpecWithToken())
                    .body(createChatRequestDto.toJson())
                .when()
                    .post(ChatEndpoint.CREATE_CHAT)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(201)
                    .extract()
                    .as(ChatInformationDto.class);
    }

    @Step("Вызов API: удалить чат с id = [{chatId}]")
    public static void deleteChat(int chatId) {
        given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", chatId)
                .when()
                    .delete(ChatEndpoint.DELETE_CHAT)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(204);
    }

    @Step("Вызов API: удалить все чаты пользователя")
    public static void deleteAllChats() {
        List<ChatInformationDto> existChats = getListChats().getResults();
        List<String> existChatsIds = existChats.stream()
                .map(chat -> String.valueOf(chat.getId()))
                .collect(Collectors.toList());
        String requestBody = String.format("{\"chats_id\": [%s]}", String.join(",", existChatsIds));

        given().spec(defaultRequestSpecWithToken())
                    .body(requestBody)
                .when()
                    .delete(ChatEndpoint.DELETE_SOME_CHATS)
                .then()
                    .spec(defaultResponseSpec())
                .statusCode(204);
    }

}
