package zone.sfera.tests.api.base;

import zone.sfera.tests.api.auth.login.LoginApi;
import zone.sfera.tests.api.auth.login.dto.LoginRequestDto;
import zone.sfera.tests.config.Credentials;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public abstract class BaseApi {

    protected static RequestSpecification defaultRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL).build();
    }

    protected static RequestSpecification defaultRequestSpecWithToken() {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .profileId(Credentials.sferaId.profileId())
                .password(Credentials.sferaId.password())
                .deviceToken("device_token").build();
        String token = LoginApi.login(loginRequestDto).getToken();

        return defaultRequestSpec().header("Authorization", token);
    }

    protected static ResponseSpecification defaultResponseSpec() {
        return new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }

}
