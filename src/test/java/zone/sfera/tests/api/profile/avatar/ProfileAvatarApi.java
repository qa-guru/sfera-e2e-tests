package zone.sfera.tests.api.profile.avatar;

import zone.sfera.tests.api.base.BaseApi;
import zone.sfera.tests.api.profile.avatar.dto.UploadAvatarRequestDto;
import zone.sfera.tests.config.Credentials;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ProfileAvatarApi extends BaseApi {

    @Step("Вызов API: получить список аваторов [ids] пользователя")
    public static List<Integer> getAvatars() {
        JsonPath jsonPath = given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", Credentials.sferaId.profileId())
                .when()
                    .get(ProfileAvatarEndpoint.GET_AVATARS)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200)
                    .extract().jsonPath();
        List<List<Integer>> avatarsList = jsonPath.getList("data.content.id");
        return avatarsList.stream()
                .map(list -> list.get(0))
                .collect(Collectors.toList());
    }

    @Step("Вызов API: получить рейтинги [ratings] пользователя")
    public static List<Float> getRating() {
        JsonPath jsonPath = given().spec(defaultRequestSpecWithToken())
                .pathParam("id", Credentials.sferaId.profileId())
                .when()
                .get(ProfileAvatarEndpoint.GET_AVATARS)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200)
                .extract().jsonPath();
        List<List<Float>> avatarsList = jsonPath.getList("data.content.rating");
        return avatarsList.stream()
                .map(list -> list.get(0))
                .collect(Collectors.toList());
    }

    @Step("Вызов API: загрузить аватар пользователя")
    public static void uploadAvatar(UploadAvatarRequestDto uploadAvatarRequestDto) {
        given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", Credentials.sferaId.profileId())
                    .body(uploadAvatarRequestDto.toJson())
                .when()
                    .post(ProfileAvatarEndpoint.UPLOAD_AVATAR)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200);
    }

    @Step("Вызов API: удалить у пользователя аватар с id = [{avatarId}]")
    public static void deleteAvatar(int avatarId) {
        given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", Credentials.sferaId.profileId())
                    .queryParam("avatarId", avatarId)
                .when()
                    .delete(ProfileAvatarEndpoint.DELETE_AVATAR)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200);
    }

    @Step("Вызов API: удалить все аватары у пользователя")
    public static void deleteAllAvatars() {
        List<Integer> avatars = getAvatars();
        for (Integer avatar : avatars)
            deleteAvatar(avatar);
    }
}
