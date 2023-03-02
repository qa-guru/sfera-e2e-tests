package zone.sfera.tests.api.profile.moments.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DataRootDto extends BaseDto {

    private DataDto[] data;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }
}
