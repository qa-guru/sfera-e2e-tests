package zone.sfera.tests.tests.android.login;

import zone.sfera.tests.allure.AllureFeature;
import zone.sfera.tests.allure.AllureOwner;
import zone.sfera.tests.allure.AllureTag;
import zone.sfera.tests.annotation.Id;
import zone.sfera.tests.tests.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Owner(AllureOwner.DIKAYA)
@Feature(AllureFeature.LOGIN)       @Tag(AllureTag.ANDROID)
@Story("Выход из учетной записи")   @Tag(AllureTag.LOGIN)
class LogoutTest extends BaseTest {

    @Test
    @Id(1017)
    @DisplayName("Выйти из учетной записи")
    void logout() {
        successLoginBySferaId()
                .openMenu()
                .logout();
        loginScreen.checkBeLoaded();
    }

}
