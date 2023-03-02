package zone.sfera.tests.annotation.avatar;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ExtendWith(AvatarExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithAvatar {
    boolean emptyAvatar() default false;
    String avatarUrl() default "https://s00.yaplakal.com/pics/pics_preview/2/3/9/13761932.jpg";
}
