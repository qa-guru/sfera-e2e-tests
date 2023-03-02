package zone.sfera.tests.annotation.moments;

import zone.sfera.tests.api.profile.moments.ProfileMomentsApi;
import zone.sfera.tests.api.profile.moments.dto.DataRootDto;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class MomentsExtention implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getRequiredTestMethod().isAnnotationPresent(WithMoments.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(DataRootDto[].class);
    }

    @Override
    public DataRootDto[] resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        WithMoments annotation = extensionContext.getRequiredTestMethod().getAnnotation(WithMoments.class);

        if (annotation == null)
            throw new ParameterResolutionException("WithMoments annotation must be applied on the test method");

        /*ProfileMomentsApi.deleteAllMoments();
        if (!annotation.emptyMoments()) {
            String momentUrl = annotation.momentUrl();
            VideoDto video = VideoDto.builder()
                    .orig(momentUrl)
                    .preview(momentUrl).build();
            UploadMomentRequestDto momentsDto = UploadMomentRequestDto.builder()
                    .video(video).build();
            ProfileMomentsApi.uploadMoment();

        }*/

        return ProfileMomentsApi.getCurrentMoments();
    }
}



