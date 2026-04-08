package trail.controller;

import org.junit.jupiter.api.Test;
import trail.model.State;

import static org.junit.jupiter.api.Assertions.*;

class PersistStateTest {

    @Test
    void shouldHaveSameStateAfterSaveSerializeAndDeserializeLoad() {
        PersistState persistState = new PersistState("./state-test.json");
        State state = new State(12, 3500, 0, 65, 76);
        persistState.save(state);
        assertEquals(state, persistState.load());
    }

    @Test
    void shouldNotSerializeInvalidPath() {
        PersistState persistState = new PersistState("./invalid/state-test.json");
        State state = new State(
                12, 3500, 0, 65, 76);
        assertThrows(IllegalStateException.class, () -> persistState.save(state));
    }

    @Test
    void shouldNotDeserializeInvalidPath() {
        PersistState persistState = new PersistState("./state-test.json");
        State state = new State(
                12, 3500, 0, 65, 76);
        persistState.save(state);
        persistState = new PersistState("./invalid/state-test.json");
        assertThrows(IllegalStateException.class, persistState::load);
    }
}