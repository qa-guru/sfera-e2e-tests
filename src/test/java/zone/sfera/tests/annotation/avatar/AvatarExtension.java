package zone.sfera.tests.annotation.avatar;

import zone.sfera.tests.api.profile.avatar.ProfileAvatarApi;
import zone.sfera.tests.api.profile.avatar.dto.UploadAvatarRequestDto;
import zone.sfera.tests.api.profile.information.ProfileInformationApi;
import zone.sfera.tests.api.profile.information.dto.AvatarDto;
import org.junit.jupiter.api.extension.*;

public class AvatarExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getRequiredTestMethod().isAnnotationPresent(WithAvatar.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(AvatarDto.class);
    }

    @Override
    public AvatarDto resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        WithAvatar annotation = extensionContext.getRequiredTestMethod().getAnnotation(WithAvatar.class);

        if (annotation == null)
            throw new ParameterResolutionException("WithAvatar annotation must be applied on the test method");

        ProfileAvatarApi.deleteAllAvatars();
        if (!annotation.emptyAvatar()) {
            String avatarUrl = annotation.avatarUrl();
            UploadAvatarRequestDto avatarDto = UploadAvatarRequestDto.builder()
                    .orig(avatarUrl)
                    .preview(avatarUrl)
                    .large(avatarUrl)
                    .medium(avatarUrl)
                    .small(avatarUrl).build();
            ProfileAvatarApi.uploadAvatar(avatarDto);
        }

        return ProfileInformationApi.getCurrentProfile().getAvatar();
    }

}
