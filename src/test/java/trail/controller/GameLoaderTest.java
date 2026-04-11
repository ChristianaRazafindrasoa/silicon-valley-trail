package trail.controller;

import org.junit.jupiter.api.Test;
import trail.model.State;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameLoaderTest {

    @Test
    void shouldHaveSameStateAfterSaveSerializeAndDeserializeLoad() {
        GameLoader gameLoader = new GameLoader("./state-test.json");
        State state = new State(12, 3500, 100, 65, 76);
        gameLoader.save(state);
        assertEquals(Optional.of(state), gameLoader.load());
    }

    @Test
    void shouldNotSerializeInvalidPath() {
        GameLoader gameLoader = new GameLoader("./invalid/state-test.json");
        State state = new State(
                12, 3500, 100, 65, 76);
        assertThrows(IllegalStateException.class, () -> gameLoader.save(state));
    }

    @Test
    void shouldNotDeserializeInvalidPath() {
        GameLoader gameLoader = new GameLoader("./state-test.json");
        State state = new State(
                12, 3500, 100, 65, 76);
        gameLoader.save(state);
        gameLoader = new GameLoader("./invalid/state-test.json");
        assertTrue(gameLoader.load().isEmpty());
    }
}