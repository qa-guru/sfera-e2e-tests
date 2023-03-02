package zone.sfera.tests.api.profile.following;

import zone.sfera.tests.api.base.BaseApi;
import zone.sfera.tests.api.profile.following.dto.GetFollowingsResponseDto;
import zone.sfera.tests.api.profile.information.dto.UserProfileDto;
import zone.sfera.tests.config.Credentials;
import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.RestAssured.given;

public class FollowingApi extends BaseApi {

    @Step("Вызов API: получить список подписок (пользователи, на которых подписан ты)")
    public static GetFollowingsResponseDto getFollowings() {
        return given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", Credentials.sferaId.profileId())
                    .queryParam("limit", 50)
                .when()
                    .get(FollowingEndpoint.GET_FOLLOWINGS)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200)
                    .extract().as(GetFollowingsResponseDto.class);
    }

    @Step("Вызов API: подписаться на пользователя [{followingProfileId}]")
    public static void follow(int followingProfileId) {
        given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", Credentials.sferaId.profileId())
                    .queryParam("followId", followingProfileId)
                .when()
                    .post(FollowingEndpoint.FOLLOW)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200);
    }

    @Step("Вызов API: отписаться от пользователя [{followingProfileId}]")
    public static void unfollow(int followingProfileId) {
        given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", Credentials.sferaId.profileId())
                    .queryParam("followId", followingProfileId)
                .when()
                    .delete(FollowingEndpoint.UNFOLLOW)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200);
    }

    @Step("Отписаться от всех пользователей")
    public static void unfollowAll() {
        List<UserProfileDto> followings = getFollowings().getData();
        followings.forEach(following -> {
            unfollow(following.getId());
        });
    }

}
