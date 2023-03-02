package zone.sfera.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/drivers/env.properties")
public interface EnvConfig extends Config {

    @Key("base_api_uri")
    String baseApiURI();

    @Key("base_grpc_uri")
    String baseGrpcURI();

    @Key("base_grpc_port")
    int baseGrpcPort();

}
