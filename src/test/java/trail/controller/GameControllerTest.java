package trail.controller;

import org.junit.jupiter.api.Test;
import trail.model.State;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    GameController gameController = new GameController();

    @Test
    void shouldNotHaveSameStateAfterTravel() {
        State state1 = new State(12, 3500, 0, 65, 76);
        State state2 = new State(12, 3500, 0, 65, 76);
        gameController.travel(state1);
        assertNotEquals(state1.getCurrentCity(), state2.getCurrentCity());
        assertNotEquals(state1, state2);
    }

    @Test
    void shouldNotHaveSameStateAfterWork() {
        State state1 = new State(12, 3500, 0, 65, 76);
        State state2 = new State(12, 3500, 0, 65, 76);
        gameController.work(state1);
        assertEquals(state1.getCurrentCity(), state2.getCurrentCity());
        assertNotEquals(state1, state2);
    }

    @Test
    void shouldNotHaveSameStateAfterPromote() {
        State state1 = new State(12, 3500, 0, 65, 76);
        State state2 = new State(12, 3500, 0, 65, 76);
        gameController.promote(state1);
        assertEquals(state1.getCurrentCity(), state2.getCurrentCity());
        assertNotEquals(state1, state2);
    }

    @Test
    void shouldNotHaveSameStateAfterRecharge() {
        State state1 = new State(12, 3500, 0, 65, 76);
        State state2 = new State(12, 3500, 0, 65, 76);
        gameController.recharge(state1);
        assertEquals(state1.getCurrentCity(), state2.getCurrentCity());
        assertNotEquals(state1, state2);
    }

    @Test
    void shouldEndGameWhenDestinationIsReached() {
        State state = new State(12, 3500, 30, 65, 76);
        while (state.hasNextCity()) {
            state.getNextCity();
        }
        assertTrue(gameController.isEndOfGame(state));
    }

    @Test
    void shouldEndGameWhenBatteryIsFlat() {
        State state = new State(12, 3500, 0, 65, 76);
        assertTrue(gameController.isEndOfGame(state));
    }

    @Test
    void shouldNotEndGameWhenWinningConditionsNotMet() {
        State state = new State(12, 3500, 30, 65, 76);
        assertFalse(gameController.isEndOfGame(state));
    }
}