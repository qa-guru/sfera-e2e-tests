package zone.sfera.tests.annotation.photo;

import zone.sfera.tests.api.profile.photo.ProfilePhotoApi;
import zone.sfera.tests.api.profile.photo.dto.PhotoDto;
import zone.sfera.tests.api.profile.photo.dto.UploadPhotoRequestDto;
import org.junit.jupiter.api.extension.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PhotoGalleryExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getRequiredTestMethod().isAnnotationPresent(WithPhotoGallery.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(PhotoDto[].class);
    }

    @Override
    public PhotoDto[] resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        WithPhotoGallery annotation = extensionContext.getRequiredTestMethod().getAnnotation(WithPhotoGallery.class);

        if (annotation == null)
            throw new ParameterResolutionException("WithPhotoGallery annotation must be applied on the test method");

        ProfilePhotoApi.deleteAllPhotos();
        if (!annotation.emptyGallery()) {
            for (int i = 1; i <= annotation.uploadPhotosCount(); i++) {
                int randomPhotoIndex = new Random().nextInt(PHOTO_URLS.size() - 1);
                String photoUrl = PHOTO_URLS.get(randomPhotoIndex);

                UploadPhotoRequestDto photoDto = UploadPhotoRequestDto.builder()
                        .orig(photoUrl)
                        .preview(photoUrl).build();
                ProfilePhotoApi.uploadPhoto(photoDto);
            }
        }
        return ProfilePhotoApi.getPhotos();
    }

    private static final List<String> PHOTO_URLS = Arrays.asList(
            "https://hdfon.ru/wp-content/uploads/hdfon.ru-949554174-1024x576.jpg",
            "https://i.pinimg.com/originals/29/0a/0b/290a0bb6268ea81c5f14a8d4f690ff2e.jpg",
            "https://bithub.pl/wp-content/uploads/2020/08/cute_baby_bear-1024x576-1.jpg",
            "https://c.pxhere.com/photos/ac/0f/bear_cub_brown_rolling_wildlife_nature_cute_young-898959.jpg!s1",
            "https://s3.tradingview.com/userpics/1653180-btHP_orig.png",
            "https://i.ytimg.com/vi/sHgX64R78nE/hqdefault.jpg");

}
