package zone.sfera.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/drivers/mobile.properties")
public interface MobileDriverConfig extends Config {
    String platformName();
    String platformVersion();
    String deviceName();
    String locale();
    String language();
    String appPackage();
    String appActivity();
    String appUrl();
    String appPath();
    String serverUrl();
}
