package zone.sfera.tests.api.profile.photo;

import zone.sfera.tests.api.base.BaseApi;
import zone.sfera.tests.api.profile.photo.dto.PhotoDto;
import zone.sfera.tests.api.profile.photo.dto.UploadPhotoRequestDto;
import zone.sfera.tests.config.Credentials;
import io.qameta.allure.Step;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ProfilePhotoApi extends BaseApi {

    @Step("Вызов API: получить список фотографий пользователя")
    public static PhotoDto[] getPhotos() {
        return given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", Credentials.sferaId.profileId())
                .when()
                    .get(ProfilePhotoEndpoint.GET_PHOTOS)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200)
                    .extract().as(PhotoDto[].class);
    }

    @Step("Вызов API: загрузить фотографию в профиль")
    public static void uploadPhoto(UploadPhotoRequestDto uploadPhotoRequestDto) {
        given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", Credentials.sferaId.profileId())
                    .body(uploadPhotoRequestDto.toJson())
                .when()
                    .post(ProfilePhotoEndpoint.UPLOAD_PHOTO)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200);
    }

    @Step("Вызов API: удалить у пользователя фотографию с id = [{photoId}]")
    public static void deletePhoto(int photoId) {
        given().spec(defaultRequestSpecWithToken())
                    .pathParam("id", Credentials.sferaId.profileId())
                    .queryParam("photoId", photoId)
                .when()
                    .delete(ProfilePhotoEndpoint.DELETE_PHOTO)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200);
    }

    @Step("Вызов API: удалить все фотографии у пользователя")
    public static void deleteAllPhotos() {
        List<Integer> existsPhotoIds = Arrays.stream(getPhotos())
                .map(PhotoDto::getId)
                .collect(Collectors.toList());
        existsPhotoIds.forEach(ProfilePhotoApi::deletePhoto);
    }

}
