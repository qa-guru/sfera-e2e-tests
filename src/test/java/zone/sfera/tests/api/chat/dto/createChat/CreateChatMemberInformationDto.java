package zone.sfera.tests.api.chat.dto.createChat;

import com.google.gson.annotations.SerializedName;
import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class CreateChatMemberInformationDto extends BaseDto {

    private int user;
    private int id;

    @SerializedName("is_owner")
    private boolean isOwner;

    private String role;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
