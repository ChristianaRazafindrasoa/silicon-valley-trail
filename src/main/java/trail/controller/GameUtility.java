package trail.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class GameUtility {
    public static Optional<String> getEnvVariable(String key) {
        try {
            String contents = Files.readString(Path.of(GameConstants.ENV_FILE_PATH));
            for (String line : contents.split("\n")) {
                String[] parts = line.split("=");
                if (parts[0].equalsIgnoreCase(key)) {
                    return Optional.of(parts[1]);
                }
            }
        } catch (IOException ex) {}
        
        return Optional.empty();
    }
}
