package zone.sfera.tests.api.profile.avatar.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class UploadAvatarRequestDto extends BaseDto {

    private String orig;
    private String preview;
    private String large;
    private String medium;
    private String small;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
