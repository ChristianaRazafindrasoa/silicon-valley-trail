package trail.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import trail.model.State;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class StateSerializer {
    private final String filePath;

    public StateSerializer(String filePath) {
        this.filePath = filePath;
    }

    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public void serialize(State state) {
        try {
            String json = gson.toJson(state);
            try (PrintWriter writer = new PrintWriter(filePath)) {
                writer.write(json);
            }
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("Failed to save game", ex);
        }
    }

    public State deserialize() {
        try {
            String json = Files.readString(Path.of(filePath));
            return gson.fromJson(json, State.class);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load game", ex);
        }
    }
}
