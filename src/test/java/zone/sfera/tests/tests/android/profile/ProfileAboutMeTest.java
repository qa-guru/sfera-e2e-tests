package zone.sfera.tests.tests.android.profile;

import zone.sfera.tests.allure.AllureFeature;
import zone.sfera.tests.allure.AllureOwner;
import zone.sfera.tests.allure.AllureTag;
import zone.sfera.tests.annotation.Id;
import zone.sfera.tests.annotation.information.WithProfile;
import zone.sfera.tests.api.profile.information.ProfileInformationApi;
import zone.sfera.tests.api.profile.information.dto.UserProfileDto;
import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.enums.Users;
import zone.sfera.tests.pages.android.menu.MenuItem;
import zone.sfera.tests.tests.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Owner(AllureOwner.DIKAYA)
@Feature(AllureFeature.PROFILE)             @Tag(AllureTag.ANDROID)
@Story("Профиль пользователя: обо мне")     @Tag(AllureTag.PROFILE)
class ProfileAboutMeTest extends BaseTest {

    private static final String ABOUT_ME_PLACEHOLDER = "About me";

    @Test
    @Id(1018)
    @WithProfile
    @DisplayName("Удаление описания из блока 'Обо мне'")
    void deleteAboutMeInformation(UserProfileDto profile) {
        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        profileScreen.checkAboutMeInformationIsEquals(profile.getAbout())
                .editAboutMeInformation("")
                .checkAboutMeInformationIsEquals(ABOUT_ME_PLACEHOLDER);

        step("Проверить (API), что описание [Обо мне] удалено", () -> {
            UserProfileDto actualProfile = ProfileInformationApi.getCurrentProfile();
            assertTrue(actualProfile.getAbout().isEmpty());
        });
    }

    @Test
    @Id(1019)
    @WithProfile(about = "")
    @DisplayName("Добавление описания в блок 'Обо мне'")
    void addAboutMeInformation(UserProfileDto profile) {
        String newAboutMeInformation = "Welcome! How are you?";

        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        profileScreen.checkAboutMeInformationIsEquals(ABOUT_ME_PLACEHOLDER)
                .editAboutMeInformation(newAboutMeInformation)
                .checkAboutMeInformationIsEquals(newAboutMeInformation);

        step("Проверить (API), что описание [Обо мне] успешно добавлено", () -> {
            UserProfileDto actualProfile = ProfileInformationApi.getCurrentProfile();
            assertEquals(newAboutMeInformation, actualProfile.getAbout());
        });
    }

    @Test
    @Id(1020)
    @WithProfile
    @DisplayName("Просмотр описания в блоке 'Обо мне' (гость)")
    void checkInformationFromUserProfile(UserProfileDto profile) {
        Users user = Users.GUTS;

        loginScreen.clickSignInWithSferaIdButton()
                .signIn(String.valueOf(user.getProfileId()), user.getPassword());
        asapScreen.checkBeLoaded()
                .createChat(Credentials.sferaId.profileId())
                .openAboutPartnerScreen()
                .openPartnerProfile();
        profilePartnerScreen.checkAboutMeInformationIsEquals(profile.getAbout());
    }

}
