package zone.sfera.tests.api.profile.information;

import io.qameta.allure.Step;
import zone.sfera.tests.api.base.BaseApi;
import zone.sfera.tests.api.profile.information.dto.SetLanguageRequestDto;
import zone.sfera.tests.api.profile.information.dto.SetLocationCoordinatesRequestDto;
import zone.sfera.tests.api.profile.information.dto.UpdateProfileRequestDto;
import zone.sfera.tests.api.profile.information.dto.UserProfileDto;
import zone.sfera.tests.config.Credentials;

import static io.restassured.RestAssured.given;

public class ProfileInformationApi extends BaseApi {

    @Step("Вызов API: получить информацию о пользователе")
    public static UserProfileDto getCurrentProfile() {
        return given().spec(defaultRequestSpecWithToken())
                .when()
                .get(ProfileInformationEndpoint.GET_CURRENT_PROFILE)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200)
                .extract().as(UserProfileDto.class);
    }

    @Step("Вызов API: обновить профиль пользователя")
    public static void updateProfile(UpdateProfileRequestDto updateProfileRequestDto) {
        given().spec(defaultRequestSpecWithToken())
                .pathParam("id", Credentials.sferaId.profileId())
                .body(updateProfileRequestDto.toJson())
                .when()
                .put(ProfileInformationEndpoint.UPDATE_PROFILE)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200);
    }

    @Step("Вызов API: присвоить пользователю ряд языков")
    public static void setLanguage(SetLanguageRequestDto setLanguageRequestDto) {
        given().spec(defaultRequestSpecWithToken())
                .body(setLanguageRequestDto.toJson())
                .when()
                .post(ProfileInformationEndpoint.SET_LANGUAGE)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200);
    }

    @Step("Вызов API: задать координаты местоположения пользователя")
    public static void setLocationCoordinates(SetLocationCoordinatesRequestDto setLocationCoordinatesRequestDto) {
        given().spec(defaultRequestSpecWithToken())
                .body(setLocationCoordinatesRequestDto.toJson())
                .when()
                .post(ProfileInformationEndpoint.SET_LOCATION_COORDINATES)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200);
    }

    @Step("Вызов API: задать пользователю имя или никнейм")
    public static void setNickname(UpdateProfileRequestDto updateProfileRequestDto) {
        given().spec(defaultRequestSpecWithToken())
                .pathParam("id", Credentials.sferaId.profileId())
                .body(updateProfileRequestDto.toJson())
                .when()
                .put(ProfileInformationEndpoint.UPDATE_PROFILE)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200);
    }
}