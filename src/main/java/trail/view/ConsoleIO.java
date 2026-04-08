package trail.view;

import trail.model.Event;
import trail.model.City;
import trail.model.State;

import java.util.Scanner;

public class ConsoleIO {
    private static final String STAR = "*";
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
        print("""
                1. New Game
                2. Load Game
                3. Quit
                """);
    }

    public void displayInstructions() {
        displayHeader("SILICON VALLEY TRAIL: INSTRUCTIONS");
        print("""
            Your startup team will go from San Jose to San Francisco for Demo Day.
            \nManage your resources wisely:
            💵 Cash - Spend it like you mean it
            ☕️ Coffee - Fuel the grind
            😊 Team Morale - No meltdowns allowed
            📈 Daily Active Users - Drive engagement upward
            💻 Laptop Battery - No charging during use
            \nBest of luck!""");
        enterToStart();
    }

    public void displayGameMenu(State state) {
        displayProgressBar(state);
        print("""
             ******************************************************
             \nWhat will you do:
             
             1. Travel to next location (-cash, -coffee, -morale)
             2. Work on features (-coffee, -battery, -morale)
             3. Promote product (+cash, -battery, +users)
             4. Rest and recharge (+morale, +battery)
             5. Save and exit to menu
             
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
        print("\n******************************************************");
        System.out.printf("Day %d: | City: %s", state.getDay(), state.getCurrentCity().name());
        System.out.printf("\n💵: $%d, ☕️: %d, 😊: %d/100, 📈: %d, 💻: %d/100\n",
                state.getCash(), state.getCoffee(), state.getMorale(),
                state.getUsers(), state.getBattery());
        System.out.printf("Progress to San Francisco: %d%%\n", state.getProgressToSf());
    }

    public void displayEvent(Event event) {
        String stars = "*".repeat(event.title().length() + 7);
        print(stars);
        System.out.printf("EVENT: %s \n", event.title());
        print(stars);
        for (String option : event.options()) {
           System.out.printf("\n%s", option);
        }
        print("\n");
    }

    public void displayNoCoffeeWarning() {
        String text = "WARNING: ☕️ No more coffee, " +
                "team morale is declining 2x faster until replenished\n";;
        String stars = STAR.repeat(text.length()) + "\n";
        print(stars +  text + stars);
    }

    public void displayEndOfGameMessage(State state) {
        boolean success = false;
        String text = "END OF THE GAME ";
        if (!state.hasNextCity()) {
            success = !state.notEnoughUsers();
            String message = success
                    ? "🎉: Arrived in San Francisco and nailed your Demo Day. "
                    : "📉: Arrived in San Francisco but didn't attract enough users. ";
            text += message;
        } else {
            if (state.didLaptopDie()) {
                text += "🪫: Your laptop died. ";
            }
            if (state.didCashRunOut()) {
                text += "💰: You spent all of your money. ";
            }
            if (state.didTeamQuit()) {
                text += "🏃: Your team quit. ";
            }
        }
        if (!success) {
            text += "You lose. 👎👎👎👎\n";
        } else {
            text += "You win. 👍👍👍👍\n";
        }
        String stars = STAR.repeat(text.length()) + "\n";
        print(stars +  text + stars);
    }

    public void displayTravelStatus(City current, City next) {
        String text = String.format("""
                
                🚝Your team hops on the Caltrain, leaving %s...🚝
                
                📍Arrived at %s station📍
                """, current.name(), next.name());
        print(text);
    }

    public void displayWorkStatus() {
        String text = """
                
                ⌨️Your team is working on cool features... ⌨️️
                
                🫩That session drained your team and battery🫩️️
                """;
        print(text);
    }

    public void displayPromoteStatus() {
        String text = """
                
                📢Your team is promoting your product...📢
                
                👏Attracted new users. Let's go!👏️️️
                """;
        print(text);
    }

    public void displayRechargeStatus() {
        String text = """
                
                🪫Your team is taking a well-deserved break...🪫
                
                🔋Nap was good and laptop is fully charged🔋
                """;
        print(text);
    }

    private void print(String text) {
        System.out.println(text);
    }
}
