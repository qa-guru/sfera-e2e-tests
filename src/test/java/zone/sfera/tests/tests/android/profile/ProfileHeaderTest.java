package zone.sfera.tests.tests.android.profile;

import zone.sfera.tests.allure.AllureFeature;
import zone.sfera.tests.allure.AllureOwner;
import zone.sfera.tests.allure.AllureTag;
import zone.sfera.tests.annotation.Id;
import zone.sfera.tests.annotation.following.WithFollowing;
import zone.sfera.tests.api.profile.information.ProfileInformationApi;
import zone.sfera.tests.api.profile.information.dto.SetLanguageRequestDto;
import zone.sfera.tests.api.profile.information.dto.SetLocationCoordinatesRequestDto;
import zone.sfera.tests.api.profile.information.dto.UpdateProfileRequestDto;
import zone.sfera.tests.api.profile.information.dto.UserProfileDto;
import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.enums.Language;
import zone.sfera.tests.enums.ReportReason;
import zone.sfera.tests.enums.Users;
import zone.sfera.tests.helpers.PhoneManagerHelper;
import zone.sfera.tests.pages.android.menu.MenuItem;
import zone.sfera.tests.tests.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Owner(AllureOwner.DIKAYA)
@Feature(AllureFeature.PROFILE)                 @Tag(AllureTag.ANDROID)
@Story("Профиль пользователя: шапка профиля")   @Tag(AllureTag.PROFILE)
class ProfileHeaderTest extends BaseTest {

    private final Users chatPartner = Users.ALUCARD;

    @Test
    @Id(1021)
    @DisplayName("Переход в профиль (из бокового меню)")
    void openProfileMenu() {
        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        profileScreen.checkBeLoaded();
    }

    @Test
    @Id(1022)
    @DisplayName("ID number @sfera.zone (поделиться своим профилем)")
    void shareProfile() {
        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        profileScreen.checkBeLoaded()
                .shareProfile();
    }

    @Test
    @Id(1023)
    @DisplayName("Язык пользователя")
    void setLanguage() {
        Language initialLanguage = Language.EN;
        Language addedLanguage = Language.RU;

        step("Подготовка тестовых данных: задать пользователю ряд языков", () -> {
            SetLanguageRequestDto setLanguageRequestDto = SetLanguageRequestDto.builder()
                    .language(Collections.singletonList(initialLanguage.getShortTitle()))
                    .build();
            ProfileInformationApi.setLanguage(setLanguageRequestDto);
        });

        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        profileScreen.addLanguage(addedLanguage)
                .checkLanguageIsSet(initialLanguage)
                .checkLanguageIsSet(addedLanguage);
    }

    @Test
    @Id(1039)
    @DisplayName("Язык пользователя (гость, отображение)")
    void checkLanguage() {
        Language initialLanguage = Language.ES;

        step("Подготовка тестовых данных: задать пользователю ряд языков", () -> {
            SetLanguageRequestDto setLanguageRequestDto = SetLanguageRequestDto.builder()
                    .language(Collections.singletonList(initialLanguage.getShortTitle()))
                    .build();
            ProfileInformationApi.setLanguage(setLanguageRequestDto);
        });

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

        profileScreen
                .checkLanguageIsSet(initialLanguage);
    }

    @Test
    @Id(1024)
    @DisplayName("Рейтинг пользователя")
    void rating() {
        UserProfileDto currentProfile = ProfileInformationApi.getCurrentProfile();

        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        Double value = currentProfile.getRating();
        double roundOff = (double) Math.round(value * 10.0) / 10.0;
        profileScreen.checkRatingIsEqual(roundOff);
    }

    @Test
    @Id(1025)
    @DisplayName("Локация пользователя")
    void setLocationCoordinates() {
        String addedLocation = "Moscow, Russia";

        step("Подготовка тестовых данных: изменить локацию пользователя", () -> {
            SetLocationCoordinatesRequestDto requestDto = SetLocationCoordinatesRequestDto.builder()
                    .latitude(0.0)
                    .longitude(0.0)
                    .build();
            ProfileInformationApi.setLocationCoordinates(requestDto);
        });

        successLoginBySferaId()
                .openMenu()
                .clickMenuSection(MenuItem.PROFILE);
        profileScreen.checkLocation("Not specified")
                .setLocationBySearchAddress(addedLocation)
                .checkLocation(addedLocation);
    }

    @Test
    @Id(1038)
    @DisplayName("Локация пользователя (гость, отображение)")
    void checkLocationCoordinates() {
        String location = "Barcelona, Spain";
        step("Подготовка тестовых данных: задать локацию пользователя", () -> {
            SetLocationCoordinatesRequestDto requestDto = SetLocationCoordinatesRequestDto.builder()
                    .latitude(41.40361)
                    .longitude(2.17444)
                    .build();
            ProfileInformationApi.setLocationCoordinates(requestDto);
        });

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

        step("Убедится что локация пользователя " + location, () ->
            profileScreen.checkLocation(location));
    }

    @Test
    @Id(1026)
    @DisplayName("Переход в профиль пользователя (из чата)")
    void openProfileFromChat() {
        successLoginBySferaId();
        asapScreen.createChat(chatPartner.getProfileId())
                .openAboutPartnerScreen()
                .checkNickname(chatPartner.getNickName());
    }

    @Test
    @Id(1027)
    @DisplayName("Написать (через профиль пользователя)")
    void sendMessageFromProfile() {
        String message = "Hello! How are you?";

        successLoginBySferaId();
        asapScreen.createChat(chatPartner.getProfileId())
                .sendMessage(message)
                .checkSendedMessagesContain(message);
    }

    @Test
    @Id(1028)
    @WithFollowing(emptyFollowings = true)
    @Description("баг с кнопкой subscribe")
    @DisplayName("Подписаться (через профиль пользователя)")
    void subscribeFromUserProfile() {
        successLoginBySferaId();
        asapScreen.createChat(chatPartner.getProfileId())
                .openAboutPartnerScreen()
                .openPartnerProfile()
                .subscribe();
    }

    @Test
    @Id(1029)
    @WithFollowing(withFollowingUser = Users.ALUCARD)
    @Description("баг с кнопкой subscribe")
    @DisplayName("Отписаться (через профиль пользователя)")
    void unsubscribeFromUserProfile() {
        successLoginBySferaId();
        asapScreen.createChat(Users.ALUCARD.getProfileId())
                .openAboutPartnerScreen()
                .openPartnerProfile()
                .unsubscribe();
    }

    @Test
    @Id(1030)
    @DisplayName("Копировать ID (тап на ID в Header)")
    void copyIdFromProfileByTap() {
        successLoginBySferaId();
        asapScreen.createChat(chatPartner.getProfileId())
                .openAboutPartnerScreen()
                .openPartnerProfile()
                .copyIdByTap();

        step("Проверить, что ID скопировался в буфер обмена", () -> {
            String expectedValue = String.valueOf(chatPartner.getProfileId());
            String actualValue = PhoneManagerHelper.getClipboardValue();
            assertEquals(expectedValue, actualValue);
        });
    }

    @Test
    @Id(1031)
    @DisplayName("Копировать ID (тап на кнопку 'Ещё' через профиль пользователя)")
    void copyIdFromProfileByMoreButton() {
        successLoginBySferaId();
        asapScreen.createChat(chatPartner.getProfileId())
                .openAboutPartnerScreen()
                .openPartnerProfile()
                .copyByMoreButton();

        step("Проверить, что ID скопировался в буфер обмена", () -> {
            String expectedValue = String.valueOf(chatPartner.getProfileId());
            String actualValue = PhoneManagerHelper.getClipboardValue();
            assertEquals(expectedValue, actualValue);
        });
    }

    @Test
    @Id(1032)
    @DisplayName("Отмена (тап на кнопку 'Ещё' через профиль пользователя)")
    void cancelMoreActionsModal() {
        successLoginBySferaId();
        asapScreen.createChat(chatPartner.getProfileId())
                .openAboutPartnerScreen()
                .openPartnerProfile()
                .openMoreActionsMenuAndCloseIt();
    }

    @Test
    @Id(1033)
    @DisplayName("Пожаловаться (тап на кнопку 'Ещё' через профиль пользователя)")
    void reportIssue() {
        successLoginBySferaId();
        asapScreen.createChat(chatPartner.getProfileId())
                .openAboutPartnerScreen()
                .openPartnerProfile()
                .report(ReportReason.SPAM);
    }

    @Test
    @Id(1034)
    @DisplayName("Имя или Никнейм пользователя")
    void checkNicknameUser() {
        String nickName = faker.hobbit().character();

        step("Подготовка тестовых данных: задать имя пользователя " + nickName, () -> {
            UpdateProfileRequestDto requestDto = UpdateProfileRequestDto.builder()
                    .nickName(nickName)
                    .build();
            ProfileInformationApi.setNickname(requestDto);
        });

        step("Убедится что имя у пользователя " + nickName, () -> {
            successLoginBySferaId()
                    .openMenu()
                    .clickMenuSection(MenuItem.PROFILE);
            profileScreen.checkNickname(nickName);
        });
    }

    @Test
    @Id(1040)
    @DisplayName("Имя или Никнейм пользователя (гость, отображение)")
    void checkNicknamePartner() {
        String nickName = faker.hobbit().character();

        step("Подготовка тестовых данных: задать имя пользователя " + nickName, () -> {
            UpdateProfileRequestDto requestDto = UpdateProfileRequestDto.builder()
                    .nickName(nickName)
                    .build();
            ProfileInformationApi.setNickname(requestDto);
        });

        step("Авторизоваться в приложении партнером " + Users.GUTS.getNickName(), () -> {
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

        step("Убедится что имя у пользователя " + nickName, () ->
                profileScreen.checkNickname(nickName));
    }
}
