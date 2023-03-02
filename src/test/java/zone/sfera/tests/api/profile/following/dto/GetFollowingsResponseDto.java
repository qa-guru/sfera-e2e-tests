package zone.sfera.tests.api.profile.following.dto;

import zone.sfera.tests.api.base.BaseDto;
import zone.sfera.tests.api.profile.information.dto.UserProfileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class GetFollowingsResponseDto extends BaseDto {

    private List<UserProfileDto> data;
    private int total;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
