package zone.sfera.tests.tests.android.profile.avatar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EditMediaCase {
    TO_JPG("jpg", "bear-jpg.jpg"),
    TO_JPEG("jpeg", "bear-jpeg.jpeg"),
    TO_PNG("png", "bear-png.png");

    private final String extension;
    @Getter
    private final String fileName;

    public String getMediaClasspath() {
        return "avatar/" + fileName;
    }

    @Override
    public String toString() {
        return extension;
    }

}
