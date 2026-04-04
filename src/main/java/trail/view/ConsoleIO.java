package trail.view;

import trail.model.State;

import java.util.Scanner;

public class ConsoleIO {
    Scanner console = new Scanner(System.in);

    public int getChoice(int min, int max) {
        String input = null;
        int value = 0;
        while (input == null || input.isBlank() || value < min || value > max)  {
            System.out.printf("%nEnter choice (%d-%d): ", min, max);
            input = console.nextLine();
            try {
                value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Value must be between %s and %s", min, max);
            } catch (NumberFormatException ex) {
                System.out.printf("%n%s is not a number", input);
            }
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
        System.out.println("**********************************************");
        System.out.println("\nWhat will you do:\n");
        System.out.println("1. Travel to next location (-cash, -coffee, -morale)");
        System.out.println("2. Work on features (-coffee, -battery)");
        System.out.println("3. Promote product (-cash, +users, +morale)");
        System.out.println("4. Rest and recharge (+morale, +battery)");
        System.out.println("5. Go back to menu");
        System.out.println("**********************************************");
    }

    public void enterToStart() {
        System.out.println("Press [Enter] to start...");
        console.nextLine();
    }

    public void enterToContinue() {
        System.out.println("Press [Enter] to continue...");
        console.nextLine();
    }

    public void displayProgressBar(State state) {
        System.out.println("**********************************************");
        System.out.printf("Day %d: | City: %s", state.day, State.CITIES[state.cityIndex]);
        System.out.printf("\n💵: $%d, ☕️: %d, 😊: %d/100, 📈: %d, 💻: %d/100\n",
                state.cash, state.coffee, state.teamMorale, state.dailyActiveUsers, state.laptopBattery);
    }
}
