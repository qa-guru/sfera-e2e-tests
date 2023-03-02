package zone.sfera.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/drivers/gmail.properties")
public interface GmailConfig extends Config {
    String login();
    String password();
}
