package zone.sfera.tests.api.auth.login.dto;

import com.google.gson.annotations.SerializedName;
import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto extends BaseDto {

    @SerializedName("profile_id")
    private int profileId;

    private String password;

    @SerializedName("device_token")
    private String deviceToken;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
