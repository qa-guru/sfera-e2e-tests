package zone.sfera.tests.config;

import org.aeonbits.owner.ConfigFactory;

public class Credentials {

    public static BrowserstackDriverConfig browserstack = ConfigFactory.create(BrowserstackDriverConfig.class, System.getProperties());
    public static MobileDriverConfig mobileDriver = ConfigFactory.create(MobileDriverConfig.class, System.getProperties());
    public static EnvConfig envConfig = ConfigFactory.create(EnvConfig.class, System.getProperties());
    public static GmailConfig gmail = ConfigFactory.create(GmailConfig.class, System.getProperties());
    public static SferaIdConfig sferaId = ConfigFactory.create(SferaIdConfig.class, System.getProperties());

}
