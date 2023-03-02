package zone.sfera.tests.api.chat.dto.getListChats;

import zone.sfera.tests.api.base.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class GetListChatsResponseDto extends BaseDto {

    private int count;
    private List<ChatInformationDto> results;

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

}
