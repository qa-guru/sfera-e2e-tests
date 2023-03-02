package zone.sfera.tests.api.profile.photo.dto;

import com.google.gson.annotations.SerializedName;
import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class PhotoDto extends BaseDto {

    private Integer id;
    private String orig;
    private String preview;

    @SerializedName("profile_id")
    private Integer profileId;

    private Double rating;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
