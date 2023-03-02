package zone.sfera.tests.api.chat.dto.getListChats;

import com.google.gson.annotations.SerializedName;
import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class ChatInformationDto extends BaseDto {

    private int id;
    private Double rating;

    @SerializedName("chat_name")
    private String chatName;

    @SerializedName("members_count")
    private int membersCount;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
