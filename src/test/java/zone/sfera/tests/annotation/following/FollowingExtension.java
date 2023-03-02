package zone.sfera.tests.annotation.following;

import zone.sfera.tests.api.profile.following.FollowingApi;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class FollowingExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        WithFollowing annotation = context.getRequiredTestMethod().getAnnotation(WithFollowing.class);

        if (annotation == null)
            return;

        FollowingApi.unfollowAll();
        if (!annotation.emptyFollowings()) {
            int followingProfileId = annotation.withFollowingUser().getProfileId();
            FollowingApi.follow(followingProfileId);
        }
    }

}
