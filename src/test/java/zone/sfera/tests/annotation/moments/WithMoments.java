package zone.sfera.tests.annotation.moments;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ExtendWith(MomentsExtention.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithMoments {
    boolean emptyMoments() default false;
    String momentUrl() default "https://core.stage.sfera-app.net/media/api/public/content/121151162/files/2c6833ca-1ed3-11ed-ab2d-a22e38ab46a2";
}