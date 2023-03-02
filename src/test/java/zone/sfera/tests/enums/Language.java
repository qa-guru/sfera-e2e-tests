package zone.sfera.tests.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    EN("en", "English"),
    ES("es", "Spanish"),
    RU("ru", "Russian");

    private final String shortTitle;
    private final String title;

    @Override
    public String toString() {
        return title;
    }

}
