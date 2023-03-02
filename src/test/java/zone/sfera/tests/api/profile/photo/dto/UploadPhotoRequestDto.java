package zone.sfera.tests.api.profile.photo.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class UploadPhotoRequestDto extends BaseDto {

    private String orig;
    private String preview;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
