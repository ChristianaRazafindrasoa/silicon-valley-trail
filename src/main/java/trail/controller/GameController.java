package trail.controller;

import trail.model.State;
import trail.view.ConsoleIO;

public class GameController {
    ConsoleIO io = new ConsoleIO();
    State state = new State(20, 8_000, 100, 90, 35);

    public void run() {
        boolean quit = false;
        while (!quit) {
            quit = getMainMenu();
        }
    }

    private boolean getMainMenu() {
        io.displayMainMenu();
        int choice = io.getChoice(1, 3);
        switch (choice) {
            case 1:
                startNewGame();
                break;
            case 2:
                runGameLoop();
                break;
            case 3:
                System.out.println("Goodbye. 👋");
                break;
        }
        return choice == 3;
    }

    private void startNewGame() {
        io.displayInstructions();
        io.enterToStart();
        runGameLoop();
    }

    private void runGameLoop() {
        boolean exitToMenu = false;
        while (!exitToMenu) {
            io.displayProgressBar(state);
            io.displayGameMenu();
            int choice = io.getChoice(1, 5);
            switch (choice) {
                case 1:
                    String currentCity = State.CITIES[state.cityIndex];
                    System.out.printf("\n\n🚝Your team hops on the Caltrain, leaving %s...🚝\n", currentCity);
                    state.cityIndex++;
                    state.coffee -= 2;
                    state.cash -= 500;
                    state.teamMorale -= 15;
                    System.out.printf("\n📍Arrived in %s station📍\n\n", State.CITIES[state.cityIndex]);
                    io.enterToContinue();
                    break;
                case 2:
                    System.out.println("\n⌨️Your team is working on cool features... ⌨️️️\n");
                    state.coffee -= 4;
                    state.laptopBattery -= 50;
                    state.teamMorale -= 35;
                    System.out.println("\n🫩That session drained your team and battery🫩️️️\n");
                    io.enterToContinue();
                    break;
                case 3:
                    System.out.println("\n📢Your team is promoting your product...📢\n");
                    state.cash -= 2000;
                    state.laptopBattery -= 40;
                    state.dailyActiveUsers += 70;
                    System.out.println("\n👏Attracted new users. Let's go!👏️️️\n");
                    io.enterToContinue();
                    break;
                case 4:
                    System.out.println("\n🪫Your team is taking a well-deserved break...🪫\n");
                    state.laptopBattery = 100;
                    state.teamMorale += 45;
                    System.out.println("\n🔋Nap was good and laptop is fully charged🔋\n");
                    io.enterToContinue();
                    break;
                case 5:
                    exitToMenu = true;
                    break;
            }
            if (isEndOfGame()) {
                exitToMenu = true;
            }
        }
    }

    private boolean isEndOfGame() {
        if (state.laptopBattery <= 0) {
            System.out.println("🪫: Your laptop died. You lose. 👎👎👎👎");
            return true;
        } else if (state.cash <= 0) {
            System.out.println("💰: Your spent all of your money. You lose. 👎👎👎👎");
            return true;
        } else if (state.coffee <= 0) {
            System.out.println("💀: Your team died of thirst. You lose. 👎👎👎👎");
            return true;
        } else if (state.teamMorale <= 0) {
            System.out.println("🏃: Your team quit. You lose. 👎👎👎👎");
            return true;
        } else if (state.cityIndex >= State.CITIES.length - 1) {
            if (state.dailyActiveUsers < 50) {
                System.out.println("📉: You arrived in San Francisco but didn't attract enough users. You lose. 👎👎👎👎");
                return true;
            }
            System.out.println("🎉: You arrived in San Francisco and nailed your Demo Day. You win. 👍👍👍👍");
            return true;
        }
        return false;
    }
}
