package trail.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trail.model.MapboxApi;
import trail.model.State;
import trail.view.ConsoleIO;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        ConsoleIO io = new ConsoleIO();
        MapboxApi mapbox = request -> null;
        GameEvents gameEvents = new GameEvents(mapbox, new Random());
        GameLoader gameLoader = new GameLoader(GameConstants.STATE_FILE_PATH);
        this.gameController = new GameController(io, gameEvents, gameLoader);
    }

    @Test
    void shouldNotHaveSameStateAfterTravel() {
        State state1 = new State(
                12, 3500, 100, 65, 76);
        State state2 = new State(
                12, 3500, 100, 65, 76);
        gameController.travel(state1);
        assertNotEquals(state1.getCurrentCity(), state2.getCurrentCity());
        assertTrue(state1.getCash() < state2.getCash());
    }

    @Test
    void shouldNotHaveSameStateAfterWork() {
        State state1 = new State(
                12, 3500, 100, 65, 76);
        State state2 = new State(
                12, 3500, 100, 65, 76);
        gameController.work(state1);
        assertEquals(state1.getCurrentCity(), state2.getCurrentCity());
        assertTrue(state1.getBattery() < state2.getBattery());
        assertTrue(state1.getCoffee() < state2.getCoffee());
    }

    @Test
    void shouldNotHaveSameStateAfterPromote() {
        State state1 = new State(
                12, 3500, 100, 65, 76);
        State state2 = new State(
                12, 3500, 100, 65, 76);
        gameController.promote(state1);
        assertEquals(state1.getCurrentCity(), state2.getCurrentCity());
        assertTrue(state1.getUsers() > state2.getUsers());
    }

    @Test
    void shouldNotHaveSameStateAfterRecharge() {
        State state1 = new State(
                12, 3500, 10, 65, 76);
        State state2 = new State(
                12, 3500, 10, 65, 76);
        gameController.recharge(state1);
        assertEquals(state1.getCurrentCity(), state2.getCurrentCity());
        assertTrue(state1.getBattery() > state2.getBattery());
    }

    @Test
    void shouldEndGameWhenDestinationIsReached() {
        State state = new State(
                12, 3500, 30, 65, 76);
        while (state.hasNextCity()) {
            state.getNextCity();
        }
        assertTrue(gameController.isEndOfGame(state));
    }

    @Test
    void shouldEndGameWhenBatteryIsFlat() {
        State state = new State(
                12, 3500, 0, 65, 76);
        assertTrue(gameController.isEndOfGame(state));
    }

    @Test
    void shouldEndGameWhenCashRanOut() {
        State state = new State(
                12, 0, 20, 65, 76);
        assertTrue(gameController.isEndOfGame(state));
    }

    @Test
    void shouldNotEndGameWhenWinningConditionsNotMet() {
        State state = new State(
                12, 3500, 30, 65, 76);
        assertFalse(gameController.isEndOfGame(state));
    }
}