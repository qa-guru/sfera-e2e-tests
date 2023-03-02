package zone.sfera.tests.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Users {
    GUTS(121151163, "123456", "Guts Berserk"),
    ALUCARD(121151162, "123456", "Alucard"),
    PROXY(121151164, "123456", "Ergo Proxy");

    private final int profileId;
    private final String password;
    private final String nickName;

    @Override
    public String toString() {
        return nickName;
    }

}
