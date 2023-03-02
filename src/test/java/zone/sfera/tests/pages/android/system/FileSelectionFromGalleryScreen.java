package zone.sfera.tests.pages.android.system;

import com.codeborne.selenide.Condition;
import zone.sfera.tests.pages.BaseScreen;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class FileSelectionFromGalleryScreen extends BaseScreen<FileSelectionFromGalleryScreen> {

    @AllArgsConstructor
    private enum GalleryFolder {
        RECENT("Recent"),
        IMAGES("Images"),
        DOWNLOADS("Downloads"),
        SDCARD("SDCARD");

        private final String title;

        @Override
        public String toString() {
            return title;
        }
    }

    public void selectImageFromDownloadsFolder(String fileName) {
        select(GalleryFolder.DOWNLOADS, fileName);
    }

    @Step("Выбрать из галереи [{fileName}] в секции [{galleryFolder}]")
    private void select(GalleryFolder galleryFolder, String fileName) {
        step("Открыть раздел [" + galleryFolder + "]", () -> {
            $(AppiumBy.accessibilityId("Show roots")).click();
            collectionByAndroidId("title")
                    .find(Condition.text(galleryFolder.toString()))
                    .click();
        });

        step("Выбрать файл [" + fileName + "]", () -> {
            String selectImageXpath = String.format("//*[contains(@content-desc,'%s')]", fileName);
            $(AppiumBy.xpath(selectImageXpath)).click();
        });
    }

}
