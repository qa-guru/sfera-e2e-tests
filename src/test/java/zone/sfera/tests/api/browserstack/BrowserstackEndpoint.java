package zone.sfera.tests.api.browserstack;

public class BrowserstackEndpoint {

    private static final String BASE_URI = "https://api-cloud.browserstack.com/app-automate";

    public static final String GET_VIDEO = BASE_URI + "/sessions/{sessionId}.json";
    public static final String UPLOAD_MEDIA = BASE_URI + "/upload-media";

}
