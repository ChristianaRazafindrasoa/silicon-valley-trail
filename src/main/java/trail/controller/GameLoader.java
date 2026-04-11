package trail.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import trail.model.State;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class GameLoader {
    private final String filePath;
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public GameLoader(String filePath) {
        this.filePath = filePath;
    }

    public void save(State state) {
        try {
            String json = serialize(state);
            try (PrintWriter writer = new PrintWriter(filePath)) {
                writer.write(json);
            }
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("Failed to save game", ex);
        }
    }

    public Optional<State> load() {
        try {
            String json = Files.readString(Path.of(filePath));
            return Optional.of(deserialize(json));
        } catch (Throwable ex) {
            return Optional.empty();
        }
    }

    private String serialize(State state) {
        return gson.toJson(state);
    }

    private State deserialize(String json) {
        return gson.fromJson(json, State.class);
    }
}
