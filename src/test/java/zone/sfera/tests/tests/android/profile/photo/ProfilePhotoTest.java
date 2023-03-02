package zone.sfera.tests.tests.android.profile.photo;

import zone.sfera.tests.allure.AllureFeature;
import zone.sfera.tests.allure.AllureOwner;
import zone.sfera.tests.allure.AllureTag;
import zone.sfera.tests.annotation.Id;
import zone.sfera.tests.annotation.photo.WithPhotoGallery;
import zone.sfera.tests.api.profile.photo.dto.PhotoDto;
import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.enums.Users;
import zone.sfera.tests.helpers.PhoneManagerHelper;
import zone.sfera.tests.pages.android.ProfileScreen;
import zone.sfera.tests.pages.android.menu.MenuItem;
import zone.sfera.tests.tests.BaseTest;
import zone.sfera.tests.tests.android.profile.avatar.EditMediaCase;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.sleep;
import static zone.sfera.tests.api.profile.photo.ProfilePhotoApi.getPhotos;
import static zone.sfera.tests.enums.ReportReason.ILLEGAL_ACTIVITIES;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature(AllureFeature.PROFILE)
@Tag(AllureTag.ANDROID)
@Story("Профиль пользователя: фото")
@Tag(AllureTag.PROFILE)
public class ProfilePhotoTest extends BaseTest {

    private final static int WAIT_UPDATE_PHOTO_MICRO_SECS = 5_000;
    private final static String CAMERA_INJECT_IMAGE = "src/test/resources/media/mock/image-from-camera.jpeg";

    List<Integer> listPhotoIds = Arrays.stream(getPhotos())
            .map(PhotoDto::getId)
            .collect(Collectors.toList());

    @Test
    @Id(1006)
    @Owner(AllureOwner.DIKAYA)
    @WithPhotoGallery
    @DisplayName("Пожаловаться на фото (гость)")
    void reportIssuePhoto(PhotoDto[] initPhotos) {
        step("Проверить, что у пользователя в галерее имеются фото", () ->
                assertTrue(initPhotos.length > 0));

        step("Авторизоваться в приложении", () -> {
            Users user = Users.GUTS;
            loginScreen.clickSignInWithSferaIdButton()
                    .signIn(String.valueOf(user.getProfileId()), user.getPassword());
            asapScreen.checkBeLoaded();
        });

        step("Открыть профиль пользователя-партнера", () -> {
            asapScreen.createChat(Credentials.sferaId.profileId())
                    .openAboutPartnerScreen()
                    .openPartnerProfile();
        });

        profilePartnerScreen.reportIssuePhoto(ILLEGAL_ACTIVITIES);
        // TODO: 21.08.2022 проверить что жалоба отправлена 
    }

    @Test
    @Id(1007)
    @Owner(AllureOwner.DIKAYA)
    @WithPhotoGallery(uploadPhotosCount = 1)
    @DisplayName("Удаление фото (действие над фото из коллекции при просмотре)")
    void deletePhoto(PhotoDto[] initPhotos) {
        step("Проверить, что у пользователя в галерее имеются фото", () ->
                assertTrue(initPhotos.length > 0));

        successLoginAndOpenProfile()
                .deletePhoto();

        step("Подождать, пока фото удалится", () ->
                sleep(WAIT_UPDATE_PHOTO_MICRO_SECS));

        step("Убедиться, что фотография пропала из галереи", () ->
                assertEquals(0, getPhotos().length));
    }

    @Test
    @Id(1008)
    @Owner(AllureOwner.DIKAYA)
    @WithPhotoGallery(uploadPhotosCount = 4)
    @DisplayName("Просмотр фото из коллекции (владелец)")
    void photoReview(PhotoDto[] initPhotos) {
        step("Проверить, что у пользователя в галерее имеются фото", () ->
                assertTrue(initPhotos.length > 0));

        successLoginAndOpenProfile();
        profileScreen.photoReview().checkExactPhotoOpened("1/4");
        PhoneManagerHelper.swipeFromRightToLeft();
        profileScreen.checkExactPhotoOpened("2/4");
        PhoneManagerHelper.swipeFromLeftToRight();
        profileScreen.checkExactPhotoOpened("1/4");
    }

    @Id(1009)
    @Owner(AllureOwner.DIKAYA)
    @EnumSource(EditMediaCase.class)
    @ParameterizedTest(name = "расширение [{0}]")
    @WithPhotoGallery(emptyGallery = true)
    @DisplayName("Добавление фото из галереи (действие над фото из коллекции при просмотре)")
    void addPhotoFromGallery(EditMediaCase testCase, PhotoDto[] initPhotos) {
        step("Проверить, что у пользователя отсутствуют фото", () ->
                assertTrue(initPhotos.length == 0));

        PhoneManagerHelper.uploadMedia(testCase.getMediaClasspath());

        successLoginAndOpenProfile()
                .addPhotoFromGallery()
                .selectImageFromDownloadsFolder(testCase.getFileName());

        step("Обрезать выбранное изображение", () -> {
            cropPhotoScreen.clickDoneButton();
            profileScreen.checkBeLoaded();
        });

        step("Подождать, пока фото прогрузится", () ->
                sleep(WAIT_UPDATE_PHOTO_MICRO_SECS));
        step("Проверить, что фото загружено", () ->
                assertEquals(1, getPhotos().length));
    }

    @Test
    @Id(1010)
    @Owner(AllureOwner.DIKAYA)
    @WithPhotoGallery(emptyGallery = true)
    @DisplayName("Добавление фото через камеру (действие над фото из коллекции при просмотре)")
        // Работает только через Browserstack, тк используется заглушка на камеру
    void addPhotoFromCamera(PhotoDto[] initPhotos) {
        step("Проверить, что у пользователя отсутствуют фото", () ->
                assertTrue(initPhotos.length == 0));

        PhoneManagerHelper.addCameraImageInject(CAMERA_INJECT_IMAGE);

        successLoginAndOpenProfile().addPhotoFromCamera();
        step("Обрезать выбранное изображение", () -> {
            cropPhotoScreen.clickDoneButton();
            profileScreen.checkBeLoaded();
        });

        step("Подождать, пока фото прогрузится", () ->
                sleep(WAIT_UPDATE_PHOTO_MICRO_SECS));
        step("Проверить, что фото загружено", () ->
                assertEquals(1, getPhotos().length));
    }

    @Test
    @Id(1035)
    @Owner(AllureOwner.DIKAYA)
    @WithPhotoGallery(uploadPhotosCount = 4)
    @DisplayName("Просмотр фото из коллекции (гость)")
    void photoReviewGuest(PhotoDto[] initPhotos) {
        step("Проверить, что у пользователя в галерее имеются фото", () ->
                assertTrue(initPhotos.length > 0));

        step("Авторизоваться в приложении", () -> {
            Users user = Users.GUTS;
            loginScreen.clickSignInWithSferaIdButton()
                    .signIn(String.valueOf(user.getProfileId()), user.getPassword());
            asapScreen.checkBeLoaded();
        });

        step("Открыть профиль пользователя-партнера", () -> {
            asapScreen.createChat(Credentials.sferaId.profileId())
                    .openAboutPartnerScreen()
                    .openPartnerProfile();
        });

        profileScreen.photoReview().checkExactPhotoOpened("1/4");
        PhoneManagerHelper.swipeFromRightToLeft();
        profileScreen.checkExactPhotoOpened("2/4");
        PhoneManagerHelper.swipeFromLeftToRight();
        profileScreen.checkExactPhotoOpened("1/4");
    }

    @Id(1036)
    @Owner(AllureOwner.DIKAYA)
    @EnumSource(EditMediaCase.class)
    @ParameterizedTest(name = "расширение [{0}]")
    @WithPhotoGallery(uploadPhotosCount = 1)
    @DisplayName("Замена фото из галереи (действие над фото из коллекции при просмотре)")
    void editPhotoFromGallery(EditMediaCase testCase, PhotoDto[] initPhotos) {
        step("Проверить, что у пользователя в галерее имеются фото", () ->
                assertTrue(initPhotos.length > 0));

        PhoneManagerHelper.uploadMedia(testCase.getMediaClasspath());

        successLoginAndOpenProfile()
                .editPhotoFromGallery()
                .selectImageFromDownloadsFolder(testCase.getFileName());

        step("Обрезать выбранное изображение", () -> {
            cropPhotoScreen.clickDoneButton();
            back();
            profileScreen.checkBeLoaded();
        });

        step("Подождать, пока фото прогрузится", () ->
                sleep(WAIT_UPDATE_PHOTO_MICRO_SECS));
        step("Проверить, что фото было изменено", () ->
        Assertions.assertNotEquals(initPhotos[0].getId(), listPhotoIds.get(0)));
    }

    @Test
    @Id(1037)
    @Owner(AllureOwner.DIKAYA)
    @WithPhotoGallery(uploadPhotosCount = 1)
    @DisplayName("Замена фото через камеру (действие над фото из коллекции при просмотре)")
    void editPhotoFromCamera(PhotoDto[] initPhotos) {
        step("Проверить, что у пользователя в галерее имеются фото", () ->
                assertTrue(initPhotos.length > 0));

        PhoneManagerHelper.addCameraImageInject(CAMERA_INJECT_IMAGE);

        successLoginAndOpenProfile()
                .editPhotoFromCamera();

        step("Обрезать выбранное изображение", () -> {
            cropPhotoScreen.clickDoneButton();
            back();
            profileScreen.checkBeLoaded();
        });

        step("Подождать, пока фото прогрузится", () ->
                sleep(WAIT_UPDATE_PHOTO_MICRO_SECS));
        step("Проверить, что фото было изменено", () ->
                Assertions.assertNotEquals(initPhotos[0].getId(), listPhotoIds.get(0)));
    }

    private ProfileScreen successLoginAndOpenProfile() {
        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        return profileScreen;
    }

}
