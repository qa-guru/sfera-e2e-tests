package zone.sfera.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/drivers/browserstack.properties")
public interface BrowserstackDriverConfig extends Config {

    @Key("login")
    String login();

    @Key("password")
    String password();

    @Key("app")
    String app();

    @Key("platform_name")
    String platformName();

    @Key("device")
    String deviceName();

    @Key("os_version")
    String osVersion();

    @Key("project")
    String project();

    @Key("build")
    String build();

    @Key("name")
    String name();

    @Key("url")
    String url();

}
