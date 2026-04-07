package trail.controller;

import trail.model.City;
import trail.model.Event;
import trail.model.State;
import trail.view.ConsoleIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class GameController {
    private static final String STATE_FILE_PATH = "./state.json";
    private static final String ENV_FILE_PATH = ".env";
    private static final int STARTING_COFFEE = 8;
    private static final int STARTING_CASH = 5000;
    private static final int STARTING_BATTERY = 100;
    private static final int STARTING_USERS = 20;
    private static final int STARTING_MORALE = 90;

    private static final ConsoleIO io = new ConsoleIO();
    private static final MapboxApiImpl mapbox = new MapboxApiImpl(
            HttpClient.newHttpClient(), getEnvVariable("MAPBOX_SECRET_KEY"));
    private static final GameEvents gameEvents = new GameEvents(mapbox);
    private static final StateSerializer stateSerializer = new StateSerializer(STATE_FILE_PATH);

    public void run() {
        boolean quit = false;
        while (!quit) {
            io.displayMainMenu();
            int choice = io.getChoice(1, 3);
            switch (choice) {
                case 1:
                    startNewGame();
                    break;
                case 2:
                    State state = loadGame();
                    runGameLoop(state);
                    break;
                case 3:
                    System.out.println("Goodbye. 👋");
                    quit = true;
                    break;
            }
        }
    }

     private void startNewGame() {
        State state = new State(
                STARTING_COFFEE, STARTING_CASH, STARTING_BATTERY, STARTING_MORALE, STARTING_USERS);
        io.displayInstructions();
        io.enterToStart();
        runGameLoop(state);
    }

     private void runGameLoop(State state) {
        boolean exitToMenu = false;
        while (!exitToMenu) {
            io.displayProgressBar(state);
            io.displayGameMenu();
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
            checkEndOfDay(state);
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
        Event event = gameEvents.getRandomEvent(new Random(), state);
        io.displayEvent(event);
        int eventChoice = io.getChoice(1, event.options().size());
        event.selected(eventChoice);
    }

    private void checkEndOfDay(State state) {
        state.getNextDay();
        if (state.didCoffeeRunOut()) {
            if (state.getNextConsecutiveDays() >= 2) {
                io.displayWarning(state);
            }
        } else {
            state.resetConsecutiveDays();
        }
    }

    protected boolean isEndOfGame(State state) {
        if (state.didLaptopDie()) {
            io.displayEndOfGameMessage(false, "🪫: Your laptop died.");
            return true;
        } else if (state.didCashRunOut()) {
            io.displayEndOfGameMessage(false, "💰: You spent all of your money.");
            return true;
        } else if (state.didTeamQuit()) {
            io.displayEndOfGameMessage(false, "🏃: Your team quit.");
            return true;
        } else if (!state.hasNextCity()) {
            boolean success = !state.notEnoughUsers();
            String message = success
                    ? "🎉: Arrived in San Francisco and nailed your Demo Day."
                    : "📉: Arrived in San Francisco but didn't attract enough users.";
            io.displayEndOfGameMessage(success, message);
            return true;
        }
        return false;
    }

    private void saveGame(State state) {
        stateSerializer.serialize(state);
    }

    private State loadGame() {
        return stateSerializer.deserialize();
    }

    protected static String getEnvVariable(String key) {
        try {
            String contents = Files.readString(Path.of(ENV_FILE_PATH));
            for (String line : contents.split("\n")) {
                String[] parts = line.split("=");
                if (parts[0].equalsIgnoreCase(key)) {
                    return parts[1];
                }
            }
        } catch (IOException _) {

        }
        return null;
    }
}