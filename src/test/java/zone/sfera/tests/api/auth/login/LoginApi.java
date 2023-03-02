package zone.sfera.tests.api.auth.login;

import zone.sfera.tests.api.auth.login.dto.LoginRequestDto;
import zone.sfera.tests.api.auth.login.dto.LoginResponseDto;
import zone.sfera.tests.api.base.BaseApi;

import static io.restassured.RestAssured.given;

public class LoginApi extends BaseApi {

    public static LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return given().spec(defaultRequestSpec())
                    .body(loginRequestDto.toJson())
                .when()
                    .post(LoginEndpoint.POST_LOGIN)
                .then()
                    .spec(defaultResponseSpec())
                    .statusCode(200)
                    .extract().as(LoginResponseDto.class);
    }

}
