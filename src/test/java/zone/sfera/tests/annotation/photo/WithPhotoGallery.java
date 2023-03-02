package zone.sfera.tests.annotation.photo;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ExtendWith(PhotoGalleryExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithPhotoGallery {
    boolean emptyGallery() default false;
    int uploadPhotosCount() default 2;
}
