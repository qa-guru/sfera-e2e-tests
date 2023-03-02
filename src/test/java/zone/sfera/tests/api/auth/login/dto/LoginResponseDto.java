package zone.sfera.tests.api.auth.login.dto;

import com.google.gson.annotations.SerializedName;
import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto extends BaseDto {

    @SerializedName("profile_id")
    private String profileId;

    private String token;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
