package trail.controller;

import org.junit.jupiter.api.Test;
import trail.model.State;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    GameController gameController = new GameController();

    @Test
    void shouldEndGameWhenDestinationIsReached() {
        State state = new State(12, 3500, 30, 65, 76);
        state.cityIndex = State.CITIES.length - 1;
        assertTrue(gameController.isEndOfGame(state));
    }

    @Test
    void shouldEndGameWhenBatteryIsFlat() {
        State state = new State(12, 3500, 0, 65, 76);
        state.cityIndex = State.CITIES.length / 2;
        assertTrue(gameController.isEndOfGame(state));
    }

    @Test
    void shouldNotEndGameWhenWinningConditionsNotMet() {
        State state = new State(12, 3500, 30, 65, 76);
        state.cityIndex = State.CITIES.length / 2;
        assertFalse(gameController.isEndOfGame(state));
    }
}