package zone.sfera.tests.pages.android.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MenuItem {
    PROFILE("Profile", "tvMyProfile"),
    SFERA_FEED("SFERA Feed", "tvFeedSfera"),
    ASAP("ASAP", "tvAsap"),
    CHOOSE("Choose", "tvChoose"),
    BLACK_BOX("BlackBox", "tvBlackBox"),
    MEET_NET("MeetNet", "tvMeetNet"),
    FIND_FRIENDS("Find Friends", "tvFF"),
    JOB_LITE("Job Lite", "tvJobLight"),
    VIDEOS("VideoS", "tvVideoS");

    private final String buttonTitle;
    @Getter
    private final String elementId;

    @Override
    public String toString() {
        return buttonTitle;
    }

}
