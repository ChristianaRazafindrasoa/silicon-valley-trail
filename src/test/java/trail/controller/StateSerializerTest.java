package trail.controller;

import org.junit.jupiter.api.Test;
import trail.model.State;

import static org.junit.jupiter.api.Assertions.*;

class StateSerializerTest {

    @Test
    void shouldHaveSameStateAfterSerializeAndDeserialize() {
        StateSerializer stateSerializer = new StateSerializer("./state-test.json");
        State state = new State(12, 3500, 0, 65, 76);
        stateSerializer.serialize(state);
        assertEquals(state, stateSerializer.deserialize());
    }

    @Test
    void shouldNotSerializeInvalidPath() {
        StateSerializer stateSerializer = new StateSerializer("./invalid/state-test.json");
        State state = new State(
                12, 3500, 0, 65, 76);
        assertThrows(IllegalStateException.class, () -> stateSerializer.serialize(state));
    }

    @Test
    void shouldNotDeserializeInvalidPath() {
        StateSerializer stateSerializer = new StateSerializer("./state-test.json");
        State state = new State(
                12, 3500, 0, 65, 76);
        stateSerializer.serialize(state);
        stateSerializer = new StateSerializer("./invalid/state-test.json");
        assertThrows(IllegalStateException.class, stateSerializer::deserialize);
    }
}