package trail.controller;

import trail.model.City;
import trail.model.State;
import trail.view.ConsoleIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class GameController {
    private static final String FILE_PATH = "./state.json";
    private final ConsoleIO io = new ConsoleIO();
    private final GameEvents gameEvents = new GameEvents();
    private StateSerializer stateSerializer = new StateSerializer();

    public void run() throws IOException {
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

     private void startNewGame() throws FileNotFoundException {
        State state = new State(12, 5000, 100, 90, 35);
        io.displayInstructions();
        io.enterToStart();
        runGameLoop(state);
    }

     private void runGameLoop(State state) throws FileNotFoundException {
        boolean exitToMenu = false;
        while (!exitToMenu) {
            io.displayProgressBar(state);
            io.displayGameMenu();
            int choice = io.getChoice(1, 5);
            switch (choice) {
                case 1:
                    travel(state);
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
            if (isEndOfGame(state)) {
                exitToMenu = true;
                io.enterToMenu();
            }
        }
    }

    private void saveGame(State state) throws FileNotFoundException {
        String json = stateSerializer.serialize(state);
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            writer.write(json);
        }
    }

    private State loadGame() throws IOException {
        String json = Files.readString(Path.of(FILE_PATH));
        return stateSerializer.deserialize(json);
    }

    private void travel(State state) {
        City currentCity = state.getCurrentCity();
        state.cityIndex++;
        state.coffee -= 2;
        state.cash -= 500;
        state.teamMorale -= 15;
        io.displayTravelStatus(currentCity, state.getCurrentCity());
        Event event = gameEvents.getRandomEvent(new Random(), state);
        io.displayEvent(event);
        int eventChoice = io.getChoice(1, event.options().size());
        event.selected(eventChoice);
    }

    private void work(State state) {
        state.coffee -= 4;
        state.laptopBattery -= 50;
        state.teamMorale -= 35;
        io.displayWorkStatus();
    }

    private void promote(State state) {
        state.cash -= 2000;
        state.laptopBattery -= 40;
        state.dailyActiveUsers += 70;
        io.displayPromoteStatus();
    }

    private void recharge(State state) {
        state.laptopBattery = 100;
        state.teamMorale += 45;
        io.displayRechargeStatus();
    }

    protected boolean isEndOfGame(State state) {
        if (state.laptopBattery <= 0) {
            io.displayEndOfGameMessage(false, "🪫: Your laptop died.");
            return true;
        } else if (state.cash <= 0) {
            io.displayEndOfGameMessage(false, "💰: You spent all of your money.");
            return true;
        } else if (state.coffee <= 0) {
            io.displayEndOfGameMessage(false, "💀: Your team died of thirst.");
            return true;
        } else if (state.teamMorale <= 0) {
            io.displayEndOfGameMessage(false, "🏃: Your team quit.");
            return true;
        } else if (state.cityIndex >= State.CITIES.length - 1) {
            if (state.dailyActiveUsers < 50) {
                io.displayEndOfGameMessage(false, "📉: Arrived in San Francisco but didn't attract enough users.");
                return true;
            }
            io.displayEndOfGameMessage(true, "🎉: Arrived in San Francisco and nailed your Demo Day.");
            return true;
        }
        return false;
    }
}
