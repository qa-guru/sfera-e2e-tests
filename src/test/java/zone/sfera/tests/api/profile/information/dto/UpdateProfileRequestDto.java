package zone.sfera.tests.api.profile.information.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class UpdateProfileRequestDto extends BaseDto {

    private int id;
    private String about;
    private String nickName;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
