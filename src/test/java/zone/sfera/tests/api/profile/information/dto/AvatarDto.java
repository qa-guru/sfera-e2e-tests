package zone.sfera.tests.api.profile.information.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class AvatarDto extends BaseDto {

    private int id;
    private int resourceType;
    private int profileId;
    private int rated;

    private List<AvatarContentDto> content;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
