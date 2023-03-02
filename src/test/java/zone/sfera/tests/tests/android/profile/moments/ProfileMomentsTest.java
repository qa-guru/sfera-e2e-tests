package zone.sfera.tests.tests.android.profile.moments;

import zone.sfera.tests.allure.AllureFeature;
import zone.sfera.tests.allure.AllureOwner;
import zone.sfera.tests.allure.AllureTag;
import zone.sfera.tests.annotation.Id;
import zone.sfera.tests.api.profile.moments.ProfileMomentsApi;
import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.enums.Users;
import zone.sfera.tests.helpers.PhoneManagerHelper;
import zone.sfera.tests.pages.android.ProfileScreen;
import zone.sfera.tests.pages.android.menu.MenuItem;
import zone.sfera.tests.tests.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static zone.sfera.tests.api.profile.moments.ProfileMomentsApi.getDataList;
import static zone.sfera.tests.enums.ReportReason.SPAM;
import static zone.sfera.tests.helpers.PhoneManagerHelper.leftTap;
import static zone.sfera.tests.helpers.PhoneManagerHelper.rightTap;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Feature(AllureFeature.PROFILE)
@Tag(AllureTag.ANDROID)
@Story("Профиль пользователя: моменты")
@Tag(AllureTag.PROFILE)
public class ProfileMomentsTest extends BaseTest {

    private final static int WAIT_UPDATE_AVATAR_MICRO_SECS = 5_000;

    @Test
    @Id(1042)
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Добавить видео с камеры (создание момента)")
    public void addVideoFromCamera() {
        step("Удалить все моменты у пользователя", () ->
            ProfileMomentsApi.deleteAllMoments());

        step("Проверить, что у пользователя отсутствуют моменты", () ->
                assertEquals(0, getDataList().length));

        PhoneManagerHelper.uploadMedia("/mock/video1.mp4");

        successLoginAndOpenProfile().addVideoFromCamera();
        step("Обрезать выбранное видео", () -> {
            cropPhotoScreen.clickActionDoneButton().clickPublishButton();
            profileScreen.checkBeLoaded();
        });

        step("Подождать, пока видео прогрузится", () ->
                sleep(WAIT_UPDATE_AVATAR_MICRO_SECS));
        step("Проверить, что видео загружено", () ->
                assertEquals(1, getDataList().length));
    }

    @Test
    @Id(1043)
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Воспроизведение видео")
    public void runVideo() {
        step("Удалить все моменты у пользователя", () ->
                ProfileMomentsApi.deleteAllMoments());
        step("Добавить моменты пользователю", () ->
                ProfileMomentsApi.uploadMoment(1));
        step("Проверить, что у пользователя загружены моменты", () ->
                assertEquals(1, getDataList().length));

        successLoginAndOpenProfile().openVideo().checkVideoRun().checkVideoIsClosed();
    }

    @Test
    @Id(1044)
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Поставить видео на паузу")
    public void pauseVideo() {
        step("Удалить все моменты у пользователя", () ->
                ProfileMomentsApi.deleteAllMoments());
        step("Добавить моменты пользователю", () ->
                ProfileMomentsApi.uploadMoment(1));
        step("Проверить, что у пользователя загружены моменты", () ->
                assertEquals(1, getDataList().length));

        successLoginAndOpenProfile().openVideo().checkVideoRun().pauseVideo().checkVideoIsClosed();
    }

    @Test
    @Id(1045)
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Переход к следующему видео")
    public void goNextVideo() {
        step("Удалить все моменты у пользователя", () ->
                ProfileMomentsApi.deleteAllMoments());
        step("Добавить моменты пользователю", () ->
                ProfileMomentsApi.uploadMoment(2));
        step("Проверить, что у пользователя загружены моменты", () ->
                assertEquals(2, getDataList().length));

        successLoginAndOpenProfile().openVideo().checkVideoRun();
        rightTap();
        profileScreen.checkVideoIsClosed();

        // TODO: 21.08.2022 добавить проверку что видео перелистнулось
    }

    @Test
    @Id(1046)
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Переход к предыдущему видео")
    public void goPreviousVideo() {
        step("Удалить все моменты у пользователя", () ->
                ProfileMomentsApi.deleteAllMoments());
        step("Добавить моменты пользователю", () ->
                ProfileMomentsApi.uploadMoment(2));
        step("Проверить, что у пользователя загружены моменты", () ->
                assertEquals(2, getDataList().length));

        successLoginAndOpenProfile().openVideo().checkVideoRun();
        rightTap();
        profileScreen.checkVideoRun();
        leftTap();
        profileScreen.checkVideoIsClosed();

        // TODO: 21.08.2022 добавить проверку что видео перелистнулось
    }

    @Test
    @Id(1047)
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Оценить видео (гость)")
    public void rateVideo() {
        step("Удалить все моменты у пользователя", () ->
                ProfileMomentsApi.deleteAllMoments());
        step("Добавить моменты пользователю", () ->
                ProfileMomentsApi.uploadMoment(1));
        step("Проверить, что у пользователя загружены моменты", () ->
                assertEquals(1, getDataList().length));
        float oldRating = ProfileMomentsApi.getVideoRating().get(0);

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

        profileScreen.openVideo().checkVideoRun().rateVideo().checkVideoIsClosed();

        step("Проверить, что оценка видео изменилась", () ->{
            float newRating = ProfileMomentsApi.getVideoRating().get(0);
            assertNotEquals(oldRating, newRating);
        });
    }

    @Test
    @Id(1048)
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Пожаловаться на видео (гость)")
    public void reportIssueVideo() {
        step("Удалить все моменты у пользователя", () ->
                ProfileMomentsApi.deleteAllMoments());
        step("Добавить моменты пользователю", () ->
                ProfileMomentsApi.uploadMoment(1));
        step("Проверить, что у пользователя загружены моменты", () ->
                assertEquals(1, getDataList().length));

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

        profileScreen.openVideo().checkVideoRun();
        profilePartnerScreen.reportIssueVideo(SPAM);
        profileScreen.checkVideoIsClosed();

        // TODO: 21.08.2022 проверить что жалоба отправлена
    }

    @Test
    @Id(1049)
    @Owner(AllureOwner.DIKAYA)
    @DisplayName("Посмотреть просмотры видео (владелец)")
    public void checkVideoViews() {
        step("Удалить все моменты у пользователя", () ->
                ProfileMomentsApi.deleteAllMoments());
        step("Добавить моменты пользователю", () ->
                ProfileMomentsApi.uploadMoment(1));
        step("Проверить, что у пользователя загружены моменты", () ->
                assertEquals(1, getDataList().length));

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

        profileScreen.openVideo().checkVideoRun().checkVideoIsClosed();
        profileScreen.openMenu().logout();

        successLoginAndOpenProfile();
        profileScreen.openVideo().checkVideoRun();
        profileScreen.checkVideoViews(ProfileMomentsApi.getVideoViews().get(0));
    }

    private ProfileScreen successLoginAndOpenProfile() {
        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        return profileScreen;
    }
}
