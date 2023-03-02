package zone.sfera.tests.drivers;

import zone.sfera.tests.config.Credentials;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;


class MobileDriverFactory {

    public static RemoteWebDriver getAndroidDriver() {
        File app = getApp();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2); //todo Deprecated
        options.setPlatformName(Credentials.mobileDriver.platformName());
        options.setDeviceName(Credentials.mobileDriver.deviceName());
        options.setPlatformVersion(Credentials.mobileDriver.platformVersion());
        options.setApp(app.getAbsolutePath());
        options.setLocale(Credentials.mobileDriver.locale());
        options.setLanguage(Credentials.mobileDriver.language());
        options.setAppPackage(Credentials.mobileDriver.appPackage());
        options.setAppActivity(Credentials.mobileDriver.appActivity());
        options.setCapability("autoGrantPermissions", "true"); //Grant all permissions for Android
        //options.setCapability("autoAcceptAlerts", "true"); //to accept all alerts for iOS apps
        //options.setCapability("autoDissmissAlerts", "true"); //to dismiss all alerts for iOS apps

        return new AndroidDriver(getAppiumServerUrl(), options);
    }

    private static URL getAppiumServerUrl() {
        try {
            return new URL(Credentials.mobileDriver.serverUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static File getApp() {
        String appPath = Credentials.mobileDriver.appPath();
        String appUrl = Credentials.mobileDriver.appUrl();

        File app = new File(appPath);
        if (!app.exists()) {
            try (InputStream in = new URL(appUrl).openStream()) {
                copyInputStreamToFile(in, app);
            } catch (IOException e) {
                throw new AssertionError("Failed to download apk", e);
            }
        }
        return app;
    }
}