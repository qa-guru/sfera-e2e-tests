package zone.sfera.tests.api.profile.information.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class SetLocationCoordinatesRequestDto extends BaseDto {

    private Double longitude;
    private Double latitude;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
