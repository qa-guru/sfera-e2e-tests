package zone.sfera.tests.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportReason {
    INAPPROPRIATE_CONTENT("Inappropriate content"),
    LOW_QUALITY_CONTENT("Low quality content"),
    ABUSIVE_LANGUAGE("Abusive language"),
    ILLEGAL_ACTIVITIES("Illegal activities"),
    PHOTO_NOT_CORRESPOND_REALITY("The photo does not correspond to reality"),
    SPAM("Spam");

    private final String description;

    @Override
    public String toString() {
        return description;
    }

}
