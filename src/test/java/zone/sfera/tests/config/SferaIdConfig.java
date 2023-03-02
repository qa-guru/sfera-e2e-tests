package zone.sfera.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/drivers/sferaid.properties")
public interface SferaIdConfig extends Config {

    @Key("profile_id")
    int profileId();

    @Key("login")
    String login();

    @Key("password")
    String password();

    @Key("grpc_token")
    String grpcToken();

}
