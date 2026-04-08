package trail.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import trail.model.State;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public State load() {
        try {
            String json = Files.readString(Path.of(filePath));
            return deserialize(json);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load game", ex);
        }
    }

    private String serialize(State state) {
        return gson.toJson(state);
    }

    private State deserialize(String json) {
        return gson.fromJson(json, State.class);
    }
}
