package zone.sfera.tests.pages.android.components;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

// todo refactor
public class PermissionsComponent {

    @Step("")
    public PermissionsComponent grantDialog() {
        $(AppiumBy.id("com.android.permissioncontroller:id/grant_dialog")).should(appear);

        return this;
    }

    @Step("")
    public PermissionsComponent permissionMessage() {
        $(AppiumBy.id("com.android.permissioncontroller:id/permission_message"))
                .shouldHave(text("Allow SFERA to record audio?"));

        return this;
    }

    @Step("")
    public PermissionsComponent permissionAllowForegroundOnlyButton() {
        $(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button")).click();

        return this;
    }

    @Step("")
    public PermissionsComponent permissionMessageAudio() {
        $(AppiumBy.id("com.android.permissioncontroller:id/permission_message"))
                .shouldHave(text("Allow SFERA to make and manage phone calls?"));

        return this;
    }

    @Step("")
    public PermissionsComponent permissionAllowButton() {
        $(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button")).click();

        return this;
    }
}