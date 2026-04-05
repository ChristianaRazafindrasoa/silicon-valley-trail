package trail.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import trail.model.State;

public class StateSerializer {
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public String serialize(State state) {
        return gson.toJson(state);
    }

    public State deserialize(String json) {
        return gson.fromJson(json, State.class);
    }
}
