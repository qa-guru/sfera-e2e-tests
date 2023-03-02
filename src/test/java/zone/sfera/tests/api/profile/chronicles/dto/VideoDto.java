package zone.sfera.tests.api.profile.chronicles.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VideoDto extends BaseDto {

    private int id;
    private String orig;
    private String preview;
    private String contentType;
    private int order;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
