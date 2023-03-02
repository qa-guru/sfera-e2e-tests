package zone.sfera.tests.api.profile.moments;

import zone.sfera.tests.api.base.BaseApi;
import zone.sfera.tests.api.profile.moments.dto.DataDto;
import zone.sfera.tests.api.profile.moments.dto.DataRootDto;
import zone.sfera.tests.api.profile.moments.dto.UploadMomentRequestDto;
import zone.sfera.tests.api.profile.moments.dto.VideoDto;
import zone.sfera.tests.config.Credentials;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ProfileMomentsApi extends BaseApi {

    @Step("Вызов API: получить список моментов")
    public static DataDto[] getDataList() {
        DataRootDto response = given().spec(defaultRequestSpecWithToken())
                .pathParam("id", Credentials.sferaId.profileId())
                .when()
                .get(ProfileMomentEndpoint.GET_MOMENTS)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200)
                .extract().as(DataRootDto.class);
        DataDto[] dataList = response.getData();
        return dataList;
    }

    @Step("Вызов API: получить Data")
    public static DataRootDto[] getCurrentMoments() {
        return given().spec(defaultRequestSpecWithToken())
                .pathParam("id", Credentials.sferaId.profileId())
                .when()
                .get(ProfileMomentEndpoint.GET_MOMENTS)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200)
                .extract().as(DataRootDto[].class);
    }

    @Step("Вызов API: получить список моментов [ids] пользователя")
    public static List<Integer> getMoments() {
        JsonPath jsonPath = given().spec(defaultRequestSpecWithToken())
                .pathParam("id", Credentials.sferaId.profileId())
                .when()
                .get(ProfileMomentEndpoint.GET_MOMENTS)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200)
                .extract().jsonPath();
        List<Integer> momentsList = jsonPath.getList("data.id");
        return momentsList;
    }

    @Step("Вызов API: загрузить момент в профиль")
    public static void uploadMoment(int uploadPhotosCount) {
        for (int i = 1; i <= uploadPhotosCount; i++) {
            String momentUrl = "https://core.stage.sfera-app.net/media/api/public/content/121151162/" +
                    "files/f3987199-2143-11ed-ab2d-a22e38ab46a2";

            VideoDto video = VideoDto.builder()
                    .orig(momentUrl)
                    .preview(momentUrl).build();
            UploadMomentRequestDto momentsDto = UploadMomentRequestDto.builder()
                    .video(video).build();

            given().spec(defaultRequestSpecWithToken())
                    .body(momentsDto.toJson())
                    .when()
                    .post(ProfileMomentEndpoint.UPLOAD_MOMENTS)
                    .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200);
        }
    }

    @Step("Вызов API: получить рейтинги [ratings] пользователя")
    public static List<Float> getVideoRating() {
        JsonPath jsonPath = given().spec(defaultRequestSpecWithToken())
                .pathParam("id", Credentials.sferaId.profileId())
                .when()
                .get(ProfileMomentEndpoint.GET_MOMENTS)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200)
                .extract().jsonPath();
        List<Float> momentsList = jsonPath.getList("data.profile.rating");
        return momentsList;
    }

    @Step("Вызов API: получить кол-во просмотров видео [views] пользователя")
    public static List<Integer> getVideoViews() {
        JsonPath jsonPath = given().spec(defaultRequestSpecWithToken())
                .pathParam("id", Credentials.sferaId.profileId())
                .when()
                .get(ProfileMomentEndpoint.GET_MOMENTS)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200)
                .extract().jsonPath();
        List<Integer> momentsList = jsonPath.getList("data.views");
        return momentsList;
    }

    @Step("Вызов API: удалить у пользователя момент с id = [{momentId}]")
    public static void deleteMoment(int momentId) {
        given().spec(defaultRequestSpecWithToken())
                .pathParam("id", momentId)
                .when()
                .delete(ProfileMomentEndpoint.DELETE_MOMENTS)
                .then()
                .spec(defaultResponseSpec())
                .statusCode(200);
    }

    @Step("Вызов API: удалить все моменты у пользователя")
    public static void deleteAllMoments() {
        List<Integer> moments = getMoments();
        for (Integer moment : moments)
            deleteMoment(moment);
    }
}
