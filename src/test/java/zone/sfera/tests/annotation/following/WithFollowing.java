package zone.sfera.tests.annotation.following;

import zone.sfera.tests.enums.Users;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ExtendWith(FollowingExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithFollowing {
    boolean emptyFollowings() default false;
    Users withFollowingUser() default Users.ALUCARD;
}
