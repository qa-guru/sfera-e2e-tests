package zone.sfera.tests.pages.android.menu;

import zone.sfera.tests.pages.BaseScreen;
import zone.sfera.tests.pages.android.login.LoginScreen;
import zone.sfera.tests.pages.android.settings.SettingsScreen;
import io.qameta.allure.Step;

public class MenuScreen extends BaseScreen<MenuScreen> {

    @Step("Открыть пункт меню [{item}]")
    public void clickMenuSection(MenuItem item) {
        elementBySferaId(item.getElementId()).click();
    }

    @Step("Открыть настройки")
    public SettingsScreen openSettings() {
        elementBySferaId("tvMenuSettings").click();
        return new SettingsScreen();
    }

    @Step("Выйти из учетной записи")
    public LoginScreen logout() {
        elementBySferaId("menuExit").click();
        elementBySferaId("btnExit").click();
        return new LoginScreen();
    }

}
