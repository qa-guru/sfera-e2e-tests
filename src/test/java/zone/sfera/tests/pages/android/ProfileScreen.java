package zone.sfera.tests.pages.android;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import zone.sfera.tests.enums.Language;
import zone.sfera.tests.pages.BaseScreen;
import zone.sfera.tests.pages.android.system.FileSelectionFromGalleryScreen;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import zone.sfera.tests.helpers.PhoneManagerHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileScreen extends BaseScreen<ProfileScreen> {

    private final SelenideElement avatar = elementBySferaId("ivAvatar");
    private final ElementsCollection photo = collectionBySferaId("ivPhoto");
    private final ElementsCollection video = collectionBySferaId("ivMoment");
    private final SelenideElement fromCameraButton = elementBySferaId("tvFirstItem");
    private final SelenideElement fromGalleryButton = elementBySferaId("tvSecondItem");

    @Step("Поделиться профилем")
    public void shareProfile() {
        step("Нажать на кнопку [Поделиться профилем]", () ->
                elementBySferaId("ivSettings").click());

        step("Проверить, что система отобразила окно [Share]", () ->
                elementByAndroidId("title").shouldHave(text("Share")));
    }

    @Step("Добавить язык [{language}]")
    public ProfileScreen addLanguage(Language language) {
        step("Нажать на кнопку [Choose]", () -> {
            elementBySferaId("tvLanguagesChoose").click();
            elementBySferaId("tvTitle").shouldHave(text("Languages you speak"));
        });

        step("Нажать на кнопку [Add language]", () ->
                collectionBySferaId("tvLanguage").last().click());

        step("Найти язык [" + language + "]. Выбрать его", () -> {
            elementBySferaId("ivSearch").click();
            elementBySferaId("etSearch").sendKeys(language.getTitle());

            elementBySferaId("tvLanguage").shouldBe(visible);
            collectionBySferaId("tvLanguage")
                    .filter(Condition.attribute("text", language.getTitle())).last()
                    .click();
            elementBySferaId("tvTitle").shouldHave(text("Languages you speak"));
        });

        step("Вернуться в профиль пользователя", () ->
                elementBySferaId("ivBack").click());
        return this;
    }

    @Step("Установить локацию пользователя по адресу [{searchAddress}]")
    public ProfileScreen setLocationBySearchAddress(String searchAddress) {
        elementBySferaId("tvLocationChoose").click();
        elementBySferaId("etPredicate").sendKeys(searchAddress);

        collectionBySferaId("bbItemLocationPickerName").find(Condition.text(searchAddress)).click();
        elementBySferaId("acceptBtn").click();
        return this;
    }

    @Step("Удалить аватар пользователя")
    public ProfileScreen deleteAvatar() {
        avatar.click();
        elementBySferaId("tvTitle").shouldHave(text("Profile photo"));
        elementBySferaId("ivSettings").click();
        elementBySferaId("tvDelete").click();
        elementBySferaId("tvTitle").shouldHave(text("Delete"));
        elementBySferaId("tvDelete").click();
        elementBySferaId("tvFullName").shouldBe(visible);
        return this;
    }

    @Step("Добавить аватар (из галлереи)")
    public FileSelectionFromGalleryScreen addAvatarFromGallery() {
        addAvatar(fromGalleryButton);
        return new FileSelectionFromGalleryScreen();
    }

    @Step("Добавить фото (из галлереи)")
    public FileSelectionFromGalleryScreen addPhotoFromGallery() {
        addPhoto(fromGalleryButton);
        return new FileSelectionFromGalleryScreen();
    }

    @Step("Добавить аватар (из камеры)")
    public CropPhotoScreen addAvatarFromCamera() {
        addAvatar(fromCameraButton);
        return takePhotoInRealCamera();
    }

    @Step("Добавить фото (из камеры)")
    public CropPhotoScreen addPhotoFromCamera() {
        addPhoto(fromCameraButton);
        return takePhotoInRealCamera();
    }

    @Step("Добавить видео (из камеры)")
    public CropPhotoScreen addVideoFromCamera() {
        addVideo(fromCameraButton);
        return takeVideoInRealCamera();
    }

    @Step("Изменить аватар на другой (из галлереи)")
    public FileSelectionFromGalleryScreen editAvatarFromGallery() {
        editAvatar(fromGalleryButton);
        return new FileSelectionFromGalleryScreen();
    }

    @Step("Добавить фото (из галлереи)")
    public FileSelectionFromGalleryScreen editPhotoFromGallery() {
        editPhoto(fromGalleryButton);
        return new FileSelectionFromGalleryScreen();
    }

    @Step("Изменить аватар на другой (из камеры)")
    public CropPhotoScreen editAvatarFromCamera() {
        editAvatar(fromCameraButton);
        return takePhotoInRealCamera();
    }

    @Step("Изменить аватар на другой (из камеры)")
    public CropPhotoScreen editPhotoFromCamera() {
        editPhoto(fromCameraButton);
        return takePhotoInRealCamera();
    }

    @Step("Сделать фото из фотоколлекции аватаром")
    public ProfileScreen makeAvatarFromPhotoCollection(int photoIndex) {
        elementBySferaId("rvPhotos").shouldBe(visible);
        collectionBySferaId("ivPhoto").get(photoIndex).click();
        elementBySferaId("ivSettings").click();
        elementBySferaId("tvMakeMain").click();
        elementBySferaId("cropView").shouldBe(visible);
        sleep(500);
        elementBySferaId("btnDone").click();
        elementBySferaId("tvFullName").shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    @Step("Отредактировать описание [Обо мне]")
    public ProfileScreen editAboutMeInformation(String aboutMeInformation) {
        elementBySferaId("etAboutMe").click();
        elementBySferaId("etAboutMe").sendKeys(aboutMeInformation);
        // Hide Keyboard
        elementBySferaId("ivLocation").click();
        return this;
    }

    @Step("Проверить, что экран профиля пользователя успешно загружен")
    public ProfileScreen checkBeLoaded() {
        elementBySferaId("tvFullName").shouldBe(visible);
        return this;
    }

    @Step("Проверить, что в профиле пользователя установлен язык [{language}]")
    public ProfileScreen checkLanguageIsSet(Language language) {
        elementBySferaId("tvLanguages").shouldHave(text(language.getTitle()));
        return this;
    }

    @Step("Проверить, что рейтинг пользователя равен [{expectedRating}]")
    public ProfileScreen checkRatingIsEqual(Double expectedRating) {
        String actualRating = elementBySferaId("tvRating").getText();
        assertEquals(String.valueOf(expectedRating), actualRating);
        return this;
    }

    @Step("Проверить, что локация пользователя [{expectedLocation}]")
    public ProfileScreen checkLocation(String expectedLocation) {
        elementBySferaId("tvLocation").shouldHave(text(expectedLocation));
        return this;
    }

    @Step("Проверить, что информация в блоке [Обо мне] = [{expectedAboutMeInformation}]")
    public ProfileScreen checkAboutMeInformationIsEquals(String expectedAboutMeInformation) {
        sleep(2000);
        elementBySferaId("etAboutMe").shouldHave(text(expectedAboutMeInformation));
        return this;
    }

    @Step("Проверить, что имя или никнейм = [{nickname}]")
    public ProfileScreen checkNickname(String nickname) {
        sleep(2000);
        elementBySferaId("tvFullName").shouldHave(text(nickname));
        return this;
    }

    private CropPhotoScreen takePhotoInRealCamera() {
        step("Разрешить приложению доступ к камере", () ->
                $(AppiumBy.xpath("//*[contains(@text, 'While using the app')]")).click());
        step("Сделать фотографию", () ->
                $(AppiumBy.id("com.google.android.GoogleCamera:id/shutter_button")).click());
        step("Подтвердить выбор", () ->
                $(AppiumBy.id("com.google.android.GoogleCamera:id/shutter_button")).click());
        return new CropPhotoScreen();
    }

    public CropPhotoScreen takeVideoInRealCamera() {
        step("Разрешить приложению доступ к камере", () ->
                $(AppiumBy.xpath("//*[contains(@text, 'While using the app')]")).click());
        step("Начать снимать видео", () ->
                $(AppiumBy.id("com.google.android.GoogleCamera:id/shutter_button")).click());
        sleep(2000);
        step("Прекратить снимать видео", () ->
                $(AppiumBy.id("com.google.android.GoogleCamera:id/shutter_button")).click());
        step("Нажать на кнопку подтвердить", () ->
                $(AppiumBy.id("com.google.android.GoogleCamera:id/shutter_button")).click());
        return new CropPhotoScreen();
    }

    private void addAvatar(SelenideElement fromSourceButton) {
        avatar.click();
        fromSourceButton.click();
    }

    private void addPhoto(SelenideElement fromSourceButton) {
        photo.get(0).click();
        fromSourceButton.click();
    }
    private void addVideo(SelenideElement fromSourceButton) {
        $(AppiumBy.id("zone.sfera:id/scrollViewMoments")).$(AppiumBy.id("zone.sfera:id/ivPhoto")).click();
        fromSourceButton.click();
    }

    private void editAvatar(SelenideElement fromSourceButton) {
        avatar.click();
        elementBySferaId("ivSettings").click();
        elementBySferaId("tvChange").click();

        elementBySferaId("tvSubtitle").shouldHave(text("Replacing will delete the previous profile photo"));
        elementBySferaId("tvReplace").click();
        fromSourceButton.click();
    }

    private void editPhoto(SelenideElement fromSourceButton) {
        photo.get(1).click();
        elementBySferaId("ivSettings").click();
        elementBySferaId("tvChange").click();
        fromSourceButton.click();
    }

    @Step("Удалить фото пользователя")
    public ProfileScreen deletePhoto() {
        elementBySferaId("rvPhotos").shouldBe(visible);
        collectionBySferaId("ivPhoto").get(1).click();
        elementBySferaId("ivSettings").click();
        elementBySferaId("tvDelete").click();
        elementBySferaId("tvTitle").shouldHave(text("Delete"));
        elementBySferaId("tvDelete").click();
        return this;
    }

    @Step("Открыть фото пользователя на просмотр")
    public ProfileScreen photoReview() {
        elementBySferaId("rvPhotos").shouldBe(visible);
        elementBySferaId("ivPhoto").click();
        return this;
    }

    @Step("Открыть видео пользователя на просмотр")
    public ProfileScreen openVideo() {
        elementBySferaId("ivMoment").shouldBe(visible);
        elementBySferaId("ivMoment").click();
        elementBySferaId("playerView").should(appear);
        $(AppiumBy.className("android.widget.ProgressBar")).should(appear);
        return this;
    }

    @Step("Убедится что видео воспроизводится")
    public ProfileScreen checkVideoRun() {
        $(AppiumBy.className("android.widget.ProgressBar")).should(disappear);
        elementBySferaId("ivMore").shouldBe(visible);
        return this;
    }

    @Step("Поставить видео на паузу")
    public ProfileScreen pauseVideo() {
        PhoneManagerHelper.longTap();
        elementBySferaId("ivMore").shouldNotBe(visible);
        // TODO: 21.08.2022 Добавить проверку что кнопка "еще" исчезает пока видео на паузе
        return this;
    }

    @Step("Убедится что экран с воспроизведением видео закрылся")
    public ProfileScreen checkVideoIsClosed() {
        elementBySferaId("playerView").should(disappear);
        elementBySferaId("ivMoment").shouldBe(visible);
        return this;
    }

    @Step("Убедится что открыто фото {photoNumber}")
    public ProfileScreen checkExactPhotoOpened(String photoNumber) {
        elementBySferaId("tvTitle").shouldHave(text(photoNumber));
        return this;
    }

    @Step("Оценить аватар пользователя")
    public ProfileScreen rateAvatar() {
        avatar.click();
        elementBySferaId("ratingBarAvatar").shouldBe(visible);
        elementBySferaId("ratingBarAvatar").click();
        elementBySferaId("ratingBarAvatar").should(disappear);
        elementBySferaId("ivRatingAvatar").shouldBe(visible);
        return this;
    }

    @Step("Оценить видео пользователя")
    public ProfileScreen rateVideo() {
        elementBySferaId("ratingBar").shouldBe(visible);
        PhoneManagerHelper.tapByCoordinates(864, 1912);
        elementBySferaId("ratingBar").should(disappear);
        elementBySferaId("tvRating").shouldBe(visible);
        return this;
    }

    @Step("Посмтотреть количество просмотров видео")
    public ProfileScreen checkVideoViews(Integer expectedViews) {
        elementBySferaId("tvViews").shouldBe(visible);
        String views = elementBySferaId("tvViews").getText();
        String actualViews = views.substring(views.length() - 1);
        assertEquals(String.valueOf(expectedViews), actualViews);
        return this;
    }

    @Step("Открыть меню")
    public ProfileScreen openMenu() {
        elementBySferaId("ivMenu").click();
        return this;
    }

    @Step("Выйти из учетной записи")
    public ProfileScreen logout() {
        elementBySferaId("tvMenuExit").click();
        elementBySferaId("btnExit").click();
        return this;
    }
}