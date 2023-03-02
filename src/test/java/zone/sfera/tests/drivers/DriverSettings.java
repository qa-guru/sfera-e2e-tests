package zone.sfera.tests.drivers;

import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSettings {

    public static RemoteWebDriver getWebDriver(String deviceProvider) {
        if (deviceProvider == null)
            throw new RuntimeException("Device should not be equal [null]");

        switch (deviceProvider) {
            case "browserstack":
                return BrowserstackDriverFactory.getAndroidDriver();
            case "mobile":
                return MobileDriverFactory.getAndroidDriver();
            default:
                throw new RuntimeException("Device [" + deviceProvider + "] not supported");
        }
    }

}
