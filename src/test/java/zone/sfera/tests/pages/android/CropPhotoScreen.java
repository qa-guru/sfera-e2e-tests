package zone.sfera.tests.pages.android;

import zone.sfera.tests.pages.BaseScreen;
import io.qameta.allure.Step;

public class CropPhotoScreen extends BaseScreen<CropPhotoScreen> {

    @Step("Crop photo: нажать на кнопку [Done]")
    public CropPhotoScreen clickDoneButton() {
        elementBySferaId("btnDone").click();
        return this;
    }

    @Step("Crop video: нажать на кнопку [Done]")
    public CropPhotoScreen clickActionDoneButton() {
        elementBySferaId("action_done").click();
        return this;
    }

    @Step("Crop video: нажать на кнопку [Publish]")
    public CropPhotoScreen clickPublishButton() {
        elementBySferaId("tvPublish").click();
        return this;
    }

}
