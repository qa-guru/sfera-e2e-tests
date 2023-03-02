package zone.sfera.tests.api.chat.dto.createChat;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class CreateChatRequestDto extends BaseDto {

    private String name;
    private List<CreateChatMemberInformationDto> members;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
