package zone.sfera.tests.api.base;

import com.google.gson.Gson;

public abstract class BaseDto {

    protected static final Gson GSON = new Gson();

    public abstract String toJson();

}
