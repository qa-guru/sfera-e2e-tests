package zone.sfera.tests.annotation.information;

import zone.sfera.tests.api.profile.information.ProfileInformationApi;
import zone.sfera.tests.api.profile.information.dto.UpdateProfileRequestDto;
import zone.sfera.tests.api.profile.information.dto.UserProfileDto;
import org.junit.jupiter.api.extension.*;

public class ProfileExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getRequiredTestMethod().isAnnotationPresent(WithProfile.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(UserProfileDto.class);
    }

    @Override
    public UserProfileDto resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        WithProfile annotation = extensionContext.getRequiredTestMethod().getAnnotation(WithProfile.class);

        if (annotation == null)
            throw new ParameterResolutionException("WithProfile annotation must be applied on the test method");

        UpdateProfileRequestDto updateProfileRequestDto = UpdateProfileRequestDto.builder()
                .about(annotation.about()).build();
        ProfileInformationApi.updateProfile(updateProfileRequestDto);

        return ProfileInformationApi.getCurrentProfile();
    }

}
