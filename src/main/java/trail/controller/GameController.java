package trail.controller;

import trail.model.City;
import trail.model.Event;
import trail.model.State;
import trail.view.ConsoleIO;

import java.util.Optional;

public class GameController {
    private final ConsoleIO io;
    private final GameEvents gameEvents;
    private final GameLoader gameLoader;

    public GameController(
            ConsoleIO io,
            GameEvents gameEvents,
            GameLoader gameLoader) {
        this.io = io;
        this.gameEvents = gameEvents;
        this.gameLoader = gameLoader;
    }

    public void run() {
        boolean quit = false;
        State state;
        while (!quit) {
            io.displayMainMenu();
            int choice = io.getChoice(1, 3);
            switch (choice) {
                case 1:
                    io.displayInstructions();
                    state = GameConstants.buildStartingState();
                    runGameLoop(state);
                    break;
                case 2:
                    state = loadGame();
                    runGameLoop(state);
                    break;
                case 3:
                    System.out.println("Goodbye. 👋");
                    quit = true;
                    break;
            }
        }
    }

     private void runGameLoop(State state) {
        boolean exitToMenu = false;
        while (!exitToMenu) {
            io.displayGameMenu(state);
            int choice = io.getChoice(1, 5);
            switch (choice) {
                case 1:
                    travel(state);
                    triggerEvent(state);
                    break;
                case 2:
                    work(state);
                    break;
                case 3:
                    promote(state);
                    break;
                case 4:
                    recharge(state);
                    break;
                case 5:
                    saveGame(state);
                    exitToMenu = true;
                    break;
            }
            processEndOfDay(state);
            if (isEndOfGame(state)) {
                exitToMenu = true;
                io.enterToMenu();
            }
        }
    }

    public void travel(State state) {
        City currentCity = state.getCurrentCity();
        state.getNextCity();
        state.adjustCoffee(-2);
        state.adjustCash(-500);
        state.adjustMorale(-15);
        io.displayTravelStatus(currentCity, state.getCurrentCity());
    }

    public void work(State state) {
        state.adjustCoffee(-4);
        state.adjustBattery(-50);
        state.adjustMorale(-35);
        io.displayWorkStatus();
    }

    public void promote(State state) {
        state.adjustCash(+1000);
        state.adjustBattery(-40);
        state.adjustUsers(+55);
        io.displayPromoteStatus();
    }

    public void recharge(State state) {
        state.adjustBattery(+100);
        state.adjustMorale(+45);
        io.displayRechargeStatus();
    }

    private void triggerEvent(State state) {
        Event event = gameEvents.getRandomEvent(state);
        io.displayEvent(event);
        int eventChoice = io.getChoice(1, event.options().size());
        event.selected(eventChoice);
    }

    private void processEndOfDay(State state) {
        state.incrementDay();
        if (state.didCoffeeRunOut()) {
            io.displayNoCoffeeWarning();
        }
    }

    protected boolean isEndOfGame(State state) {
        if (state.didLaptopDie() || state.didCashRunOut() ||
                state.didTeamQuit() || !state.hasNextCity()) {
            io.displayEndOfGameMessage(state);
            return true;
        }
        return false;
    }

    private void saveGame(State state) {
        gameLoader.save(state);
    }

    private State loadGame() {
        Optional<State> state = gameLoader.load();
        if (state.isEmpty()) {
            System.out.println("No saved game found. Loading a new game...");
            return GameConstants.buildStartingState();
        }
        return state.get();
    }
}