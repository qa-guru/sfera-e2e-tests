package zone.sfera.tests.api.profile.information.dto;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class UserProfileDto extends BaseDto {

    private int id;
    private String accountId;
    private String nickName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String about;
    private AvatarDto avatar;
    private Double rating;
    private List<String> language;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
