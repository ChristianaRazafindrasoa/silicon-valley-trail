package trail.view;

import trail.model.Event;
import trail.model.City;
import trail.model.State;

import java.util.Scanner;

public class ConsoleIO {
    private final Scanner console = new Scanner(System.in);

    public int getChoice(int min, int max) {
        String input = null;
        int value = 0;
        while (input == null || input.isBlank() || value < min || value > max)  {
            System.out.printf("%nEnter choice (%d-%d): ", min, max);
            input = console.nextLine();
            try {
                value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    System.out.println();
                    return value;
                }
                System.out.printf("Value must be between %s and %s", min, max);
            } catch (NumberFormatException ex) {
                System.out.printf("%n%s is not a number", input);
            }
            System.out.println();
        }
        return value;
    }

    public void displayHeader(String message) {
        System.out.println();
        System.out.print("*".repeat(message.length()));
        System.out.printf("%n%s%n", message);
        System.out.print("*".repeat(message.length()));
        System.out.println();
    }

    public void displayMainMenu() {
        displayHeader("SILICON VALLEY TRAIL: MAIN MENU");
        System.out.println("\n1. New Game");
        System.out.println("2. Load Game");
        System.out.println("3. Quit");
    }

    public void displayInstructions() {
        displayHeader("SILICON VALLEY TRAIL: INSTRUCTIONS");
        System.out.println("\nYour startup team will go from San Jose to San Francisco for Demo Day.\n");
        System.out.println("Manage your resources wisely: ");
        System.out.println("💵 Cash - Spend it like you mean it");
        System.out.println("☕️ Coffee - Fuel the grind");
        System.out.println("😊 Team Morale - No meltdowns allowed");
        System.out.println("📈 Daily Active Users - Drive engagement upward");
        System.out.println("💻 Laptop Battery - No charging during use");
        System.out.println("\nBest of luck!");
    }

    public void displayGameMenu() {
        System.out.println("******************************************************");
        System.out.println("\nWhat will you do:\n");
        System.out.println("1. Travel to next location (-cash, -coffee, -morale)");
        System.out.println("2. Work on features (-coffee, -battery)");
        System.out.println("3. Promote product (-cash, +users, +morale)");
        System.out.println("4. Rest and recharge (+morale, +battery)");
        System.out.println("5. Go back to menu");
        System.out.println("******************************************************");
    }

    public void enterToStart() {
        System.out.println("\nPress [Enter] to start...");
        console.nextLine();
    }

    public void enterToMenu() {
        System.out.println("\nPress [Enter] to go back to menu...");
        console.nextLine();
    }

    public void displayProgressBar(State state) {
        System.out.println("******************************************************");
        System.out.printf("Day %d: | City: %s", state.day, state.getCurrentCity().getName());
        System.out.printf("\n💵: $%d, ☕️: %d, 😊: %d/100, 📈: %d, 💻: %d/100\n",
                state.cash, state.coffee, state.teamMorale, state.dailyActiveUsers, state.laptopBattery);
    }

    public void displayEvent(Event event) {
        System.out.println("\n");
        System.out.println("*".repeat(event.getTitle().length() + 7));
        System.out.printf("EVENT: %s \n", event.getTitle());
        System.out.println("*".repeat(event.getTitle().length() + 7));
       for (String option : event.options()) {
           System.out.printf("\n%s", option);
       }
        System.out.println("\n");
    }

    public void displayEndOfGameMessage(boolean success, String message) {
        System.out.println("**********************************************************************");
        System.out.println("END OF THE GAME: \n");
        if (!success) {
            System.out.printf(message + " You lose. 👎👎👎👎");
        } else {
            System.out.printf(message + " You win. 👍👍👍👍");
        }
    }

    public void displayTravelStatus(City current, City next) {
        System.out.printf("\n🚝Your team hops on the Caltrain, leaving %s...🚝\n", current.getName());
        System.out.printf("\n📍Arrived at %s station📍\n", next.getName());
    }

    public void displayWorkStatus() {
        System.out.println("\n⌨️Your team is working on cool features... ⌨️️️\n");
        System.out.println("\n🫩That session drained your team and battery🫩️️️\n");
    }

    public void displayPromoteStatus() {
        System.out.println("\n📢Your team is promoting your product...📢\n");
        System.out.println("\n👏Attracted new users. Let's go!👏️️️\n");
    }

    public void displayRechargeStatus() {
        System.out.println("\n🪫Your team is taking a well-deserved break...🪫\n");
        System.out.println("\n🔋Nap was good and laptop is fully charged🔋\n");
    }
}
