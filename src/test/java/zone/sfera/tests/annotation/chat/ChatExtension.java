package zone.sfera.tests.annotation.chat;

import zone.sfera.tests.api.chat.ChatApi;
import zone.sfera.tests.api.chat.dto.createChat.CreateChatMemberInformationDto;
import zone.sfera.tests.api.chat.dto.createChat.CreateChatRequestDto;
import zone.sfera.tests.api.chat.dto.getListChats.ChatInformationDto;
import zone.sfera.tests.enums.Users;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.List;

public class ChatExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getRequiredTestMethod().isAnnotationPresent(WithChat.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(ChatInformationDto.class);
    }

    @Override
    public ChatInformationDto resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        WithChat annotation = extensionContext.getRequiredTestMethod().getAnnotation(WithChat.class);

        if (annotation == null)
            throw new ParameterResolutionException("WithChat annotation must be applied on the test method");

        ChatApi.deleteAllChats();

        Users user = annotation.withUser();
        CreateChatMemberInformationDto member = CreateChatMemberInformationDto.builder()
                .user(user.getProfileId())
                .id(user.getProfileId())
                .isOwner(false)
                .role("member").build();
        CreateChatRequestDto createChatDto = CreateChatRequestDto.builder()
                .name(user.getNickName())
                .members(List.of(member)).build();
        return ChatApi.createChat(createChatDto);
    }

}
