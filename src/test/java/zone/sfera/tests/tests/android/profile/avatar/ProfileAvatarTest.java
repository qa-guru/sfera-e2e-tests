package zone.sfera.tests.tests.android.profile.avatar;

import zone.sfera.tests.allure.AllureFeature;
import zone.sfera.tests.allure.AllureOwner;
import zone.sfera.tests.allure.AllureTag;
import zone.sfera.tests.annotation.Id;
import zone.sfera.tests.annotation.avatar.WithAvatar;
import zone.sfera.tests.annotation.photo.WithPhotoGallery;
import zone.sfera.tests.api.profile.avatar.ProfileAvatarApi;
import zone.sfera.tests.api.profile.information.ProfileInformationApi;
import zone.sfera.tests.api.profile.information.dto.AvatarDto;
import zone.sfera.tests.api.profile.photo.ProfilePhotoApi;
import zone.sfera.tests.api.profile.photo.dto.PhotoDto;
import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.enums.Users;
import zone.sfera.tests.helpers.PhoneManagerHelper;
import zone.sfera.tests.pages.android.ProfileScreen;
import zone.sfera.tests.pages.android.menu.MenuItem;
import zone.sfera.tests.tests.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Feature(AllureFeature.PROFILE)             @Tag(AllureTag.ANDROID)
@Story("Профиль пользователя: аватар")      @Tag(AllureTag.PROFILE)
class ProfileAvatarTest extends BaseTest {

    private final static int WAIT_UPDATE_AVATAR_MICRO_SECS = 5_000;
    private final static String CAMERA_INJECT_IMAGE = "src/test/resources/media/mock/image-from-camera.jpeg";

    @Id(1000)
    @Owner(AllureOwner.KIREEV)
    @WithAvatar(emptyAvatar = true)
    @EnumSource(EditMediaCase.class)
    @ParameterizedTest(name = "расширение [{0}]")
    @DisplayName("Добавление аватара из галереи:")
    void addAvatarFromGallery(EditMediaCase testCase, AvatarDto initAvatar) {
        step("Проверить, что у пользователя отсутствует аватар", () ->
                Assertions.assertNull(initAvatar));
        PhoneManagerHelper.uploadMedia(testCase.getMediaClasspath());

        successLoginAndOpenProfile()
                .addAvatarFromGallery()
                .selectImageFromDownloadsFolder(testCase.getFileName());
        step("Обрезать выбранное изображение", () -> {
            cropPhotoScreen.clickDoneButton().clickDoneButton();
            profileScreen.checkBeLoaded();
        });

        step("Подождать, пока аватар прогрузится", () -> sleep(WAIT_UPDATE_AVATAR_MICRO_SECS));
        step("Проверить, что аватар пользователя был загружен", () ->
                Assertions.assertNotNull(getCurrentAvatar()));
    }

    @Test
    @Id(1001)
    @Owner(AllureOwner.KIREEV)
    @WithAvatar(emptyAvatar = true)
    @DisplayName("Добавление аватара через камеру")
    // Работает только через Browserstack, тк используется заглушка на камеру
    void addAvatarFromCamera(AvatarDto initAvatar) {
        step("Проверить, что у пользователя отсутствует аватар", () ->
                Assertions.assertNull(initAvatar));
        PhoneManagerHelper.addCameraImageInject(CAMERA_INJECT_IMAGE);

        successLoginAndOpenProfile().addAvatarFromCamera();
        step("Обрезать выбранное изображение", () -> {
            cropPhotoScreen.clickDoneButton().clickDoneButton();
            profileScreen.checkBeLoaded();
        });

        step("Подождать, пока аватар прогрузится", () -> sleep(WAIT_UPDATE_AVATAR_MICRO_SECS));
        step("Проверить, что аватар пользователя был загружен", () ->
                Assertions.assertNotNull(getCurrentAvatar()));
    }

    @Id(1002)
    @WithAvatar
    @Owner(AllureOwner.KIREEV)
    @EnumSource(EditMediaCase.class)
    @ParameterizedTest(name = "расширение [{0}]")
    @DisplayName("Смена аватара из галереи:")
    void editAvatarFromGallery(EditMediaCase testCase, AvatarDto initAvatar) {
        PhoneManagerHelper.uploadMedia(testCase.getMediaClasspath());

        successLoginAndOpenProfile()
                .editAvatarFromGallery()
                .selectImageFromDownloadsFolder(testCase.getFileName());
        step("Обрезать выбранное изображение", () -> {
            cropPhotoScreen.clickDoneButton().clickDoneButton();
            profileScreen.checkBeLoaded();
        });

        step("Подождать, пока аватар прогрузится", () -> sleep(WAIT_UPDATE_AVATAR_MICRO_SECS));
        step("Проверить, что аватар был изменен", () -> {
            AvatarDto actualAvatar = getCurrentAvatar();
            Assertions.assertNotEquals(initAvatar.getId(), actualAvatar.getId());
        });
    }

    @Test
    @Id(1003)
    @WithAvatar
    @Owner(AllureOwner.KIREEV)
    @DisplayName("Смена аватара через камеру")
    // Работает только через Browserstack, тк используется заглушка на камеру
    void editAvatarFromCamera(AvatarDto initAvatar) {
        PhoneManagerHelper.addCameraImageInject(CAMERA_INJECT_IMAGE);

        successLoginAndOpenProfile().editAvatarFromCamera();
        step("Обрезать выбранное изображение", () -> {
            cropPhotoScreen.clickDoneButton().clickDoneButton();
            profileScreen.checkBeLoaded();
        });

        step("Подождать, пока аватар прогрузится", () -> sleep(WAIT_UPDATE_AVATAR_MICRO_SECS));
        step("Проверить, что аватар был изменен", () -> {
            AvatarDto actualAvatar = getCurrentAvatar();
            Assertions.assertNotEquals(initAvatar.getId(), actualAvatar.getId());
        });
    }

    @Test
    @Id(1004)
    @WithAvatar
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Удаление аватара")
    void deleteAvatar(AvatarDto initAvatar) {
        step("Проверить, что у пользователя установлен аватар", () ->
                Assertions.assertNotNull(initAvatar));

        successLoginAndOpenProfile();
        profileScreen.deleteAvatar();

        step("Проверить, что аватар успешно удален", () ->
                Assertions.assertNull(getCurrentAvatar()));
    }

    @Test
    @Id(1005)
    @Owner(AllureOwner.KIREEV)
    @WithPhotoGallery
    @WithAvatar(emptyAvatar = true)
    @DisplayName("Сделать главным фото (действие над фото из коллекции при просмотре)")
    void makeAvatarFromPhotoCollection(AvatarDto initAvatar, PhotoDto[] initPhotos) {
        step("Проверить, что у пользователя отсутствует аватар", () ->
                Assertions.assertNull(initAvatar));

        successLoginAndOpenProfile().makeAvatarFromPhotoCollection(1);

        step("Убедиться, что аватар загружен", () ->
                Assertions.assertNotNull(getCurrentAvatar()));
        step("Убедиться, что загруженная фотография была удалена из списка фотографий", () ->
                Assertions.assertEquals(initPhotos.length - 1, ProfilePhotoApi.getPhotos().length));
    }

    @Test
    @Id(1041)
    @WithAvatar
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Оценка аватара (гость)")
    void rateAvatar(AvatarDto initAvatar) {
        step("Проверить, что у пользователя установлен аватар", () ->
                Assertions.assertNotNull(initAvatar));
        float oldRating = ProfileAvatarApi.getRating().get(0);

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

        profileScreen.rateAvatar();

        step("Проверить, что оценка аватара изменилась", () ->{
        float newRating = ProfileAvatarApi.getRating().get(0);
        assertNotEquals(oldRating, newRating);
        });
    }

    private ProfileScreen successLoginAndOpenProfile() {
        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        return profileScreen;
    }

    private AvatarDto getCurrentAvatar() {
        return ProfileInformationApi.getCurrentProfile().getAvatar();
    }

}
