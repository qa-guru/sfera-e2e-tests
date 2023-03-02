package zone.sfera.tests.api.profile.information.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class AvatarContentDto extends BaseDto {

    private int id;
    private int mediaResourceId;
    private String type;
    private String data;
    private String text;
    private int mediaFileId;

    private String orig;
    private String preview;
    private String large;
    private String medium;
    private String small;
    private String half;

    private Double rating;
    private Double rated;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
