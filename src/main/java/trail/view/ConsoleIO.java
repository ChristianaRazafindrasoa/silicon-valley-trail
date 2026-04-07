package trail.view;

import trail.model.Event;
import trail.model.City;
import trail.model.State;

import java.util.Scanner;

public class ConsoleIO {
    private final String STAR = "*";
    private final Scanner console = new Scanner(System.in);

    public int getChoice(int min, int max) {
        String input = null;
        int value = 0;
        while (input == null || input.isBlank() || value < min || value > max)  {
            System.out.printf("Enter choice (%d-%d): ", min, max);
            input = console.nextLine();
            try {
                value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    System.out.println();
                    return value;
                }
                System.out.printf("Value must be between %s and %s", min, max);
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a number", input);
            }
            System.out.println();
        }
        return value;
    }

    public void displayHeader(String message) {
        String stars = "\n" + STAR.repeat(message.length()) + "\n";
        String text = stars + message + stars;
        print(text);
    }

    public void displayMainMenu() {
        displayHeader("SILICON VALLEY TRAIL: MAIN MENU");
        System.out.println("""
                            1. New Game
                            2. Load Game
                            3. Quit
                            """);
    }

    public void displayInstructions() {
        displayHeader("SILICON VALLEY TRAIL: INSTRUCTIONS");
        System.out.println("""
                            Your startup team will go from San Jose to San Francisco for Demo Day.
                            \nManage your resources wisely:
                            💵 Cash - Spend it like you mean it
                            ☕️ Coffee - Fuel the grind
                            😊 Team Morale - No meltdowns allowed
                            📈 Daily Active Users - Drive engagement upward
                            💻 Laptop Battery - No charging during use
                            \nBest of luck!""");
    }

    public void displayGameMenu() {
        System.out.println("""
                         ******************************************************
                         \nWhat will you do:
                         
                         1. Travel to next location (-cash, -coffee, -morale)
                         2. Work on features (-coffee, -battery, -morale)
                         3. Promote product (+cash, -battery, +users)
                         4. Rest and recharge (+morale, +battery)
                         5. Go back to menu
                         
                         ******************************************************
                         """);
    }

    public void enterToStart() {
        print("\nPress [Enter] to start...");
        console.nextLine();
    }

    public void enterToMenu() {
        print("\nPress [Enter] to go back to menu...");
        console.nextLine();
    }

    public void displayProgressBar(State state) {
        System.out.println("******************************************************");
        System.out.printf("Day %d: | City: %s", state.getDay(), state.getCurrentCity().getName());
        System.out.printf("\n💵: $%d, ☕️: %d, 😊: %d/100, 📈: %d, 💻: %d/100\n",
                state.getCash(), state.getCoffee(), state.getMorale(),
                state.getUsers(), state.getBattery());
        System.out.printf("Progress to San Francisco: %d%%\n", state.getProgressToSf());
    }

    public void displayEvent(Event event) {
        String stars = "*".repeat(event.getTitle().length() + 7);
        System.out.println(stars);
        System.out.printf("EVENT: %s \n", event.getTitle());
        System.out.println(stars);
       for (String option : event.options()) {
           System.out.printf("\n%s", option);
       }
        System.out.println("\n");
    }

    public void displayEndOfGameMessage(boolean success, String message) {
        String text = "END OF THE GAME:";
        if (!success) {
            text += message + " You lose. 👎👎👎👎\n";
        } else {
            text += message + " You win. 👍👍👍👍\n";
        }
        String stars = STAR.repeat(text.length()) + "\n";
        print(stars +  text + stars);
    }

    public void displayTravelStatus(City current, City next) {
        String text = String.format("\n🚝Your team hops on the Caltrain, leaving %s...🚝\n" +
                "\n📍Arrived at %s station📍\n", current.getName(), next.getName());
        print(text);
    }

    public void displayWorkStatus() {
        String text = "\n⌨️Your team is working on cool features... ⌨️️\n" +
                "\n🫩That session drained your team and battery🫩️️\n";
        print(text);
    }

    public void displayPromoteStatus() {
        String text = "\n📢Your team is promoting your product...📢\n" +
                "\n👏Attracted new users. Let's go!👏️️️\n";
        print(text);
    }

    public void displayRechargeStatus() {
        String text = "\n🪫Your team is taking a well-deserved break...🪫\n" +
                "\n🔋Nap was good and laptop is fully charged🔋\n";
        print(text);
    }

    private void print(String text) {
        System.out.println(text);
    }
}
