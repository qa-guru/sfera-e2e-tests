package zone.sfera.tests.api.browserstack;

import zone.sfera.tests.config.Credentials;

import java.io.File;

import static io.restassured.RestAssured.given;

public class BrowserstackApi {

    private static final String LOGIN = Credentials.browserstack.login();
    private static final String PASSWORD = Credentials.browserstack.password();

    public static String getVideoUrl(String sessionId) {
        return given().auth()
                    .basic(LOGIN, PASSWORD)
                    .pathParam("sessionId", sessionId)
                    .log().all()
                .when()
                    .get(BrowserstackEndpoint.GET_VIDEO)
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .path("automation_session.video_url");
    }

    public static String uploadImage(String filePath) {
        File file = new File(filePath);

        if (!file.exists())
            throw new RuntimeException("File by path [" + file.getAbsoluteFile() + "] don't exist");

        return given().auth()
                    .preemptive().basic(LOGIN, PASSWORD)
                    .multiPart("file", file)
                    .log().all()
                .when()
                    .post(BrowserstackEndpoint.UPLOAD_MEDIA)
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .path("media_url");
    }
}
