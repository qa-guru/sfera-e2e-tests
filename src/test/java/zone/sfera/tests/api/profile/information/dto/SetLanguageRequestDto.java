package zone.sfera.tests.api.profile.information.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class SetLanguageRequestDto extends BaseDto {

    private List<String> language;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
