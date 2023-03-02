package zone.sfera.tests.drivers;

import zone.sfera.tests.config.Credentials;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

class BrowserstackDriverFactory {

    // Use capabilities for Selenium 4: https://www.browserstack.com/automate/selenium-4
    // Generator capabilities: https://www.browserstack.com/automate/capabilities?tag=selenium-4

    public static RemoteWebDriver getAndroidDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("appiumVersion", "1.22.0");
        browserstackOptions.put("userName", Credentials.browserstack.login());
        browserstackOptions.put("accessKey", Credentials.browserstack.password());

        browserstackOptions.put("deviceLogs", "true");
        browserstackOptions.put("appiumLogs", "true");
        browserstackOptions.put("consoleLogs", "disable");
        browserstackOptions.put("networkLogs", "true");

        capabilities.setCapability("bstack:options", browserstackOptions);

        // Enable camera emulation
        capabilities.setCapability("browserstack.enableCameraImageInjection", true);

        // All Permission
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);

        capabilities.setCapability("platformName", Credentials.browserstack.platformName());
        capabilities.setCapability("platformVersion", Credentials.browserstack.osVersion());
        capabilities.setCapability("deviceName", Credentials.browserstack.deviceName());
        capabilities.setCapability("app", Credentials.browserstack.app());

        return new AndroidDriver(getBrowserstackUrl(), capabilities);
    }

    private static URL getBrowserstackUrl() {
        try {
            return new URL(Credentials.browserstack.url());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}