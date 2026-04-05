package trail.controller;

import trail.model.State;

import java.util.List;
import java.util.Random;

public class GameEvents {
    private static final String[] COFFEE_SHOP = new String[]{
            "Starbucks", "Philz Coffee", "Voyager Coffee", "Blue Bottle"};
    private static final String[] CELEBRITY_NAME = new String[]{
            "Ryan Roslansky", "Tim Cook", "Sam Altman", "Jeff Bezos"};
    private static final String[] CONFERENCE_TITLE = new String[]{
            "Stanford TreeHacks", "GitHub Universe", "Nvidia GTC", "TECHSPO"};
    private int coffeeShopIndex = 0;
    private int celebrityNameIndex = 0;
    private int conferenceTitleIndex = 0;

    public Event getRandomEvent(Random random, State state) {
        Event event = null;
        int rand = random.nextInt(3);
        if (rand == 1) {
            event = coffeeShopEvent(state);
            coffeeShopIndex = (coffeeShopIndex + 1) % COFFEE_SHOP.length;
        } else if (rand == 2) {
            event = celebrityEncounterEvent(state);
            celebrityNameIndex = (celebrityNameIndex + 1) % CELEBRITY_NAME.length;
        } else {
            event = freeTicketEvent(state);
            conferenceTitleIndex = (conferenceTitleIndex + 1) % CONFERENCE_TITLE.length;
        }
        return event;
    }

    private Event coffeeShopEvent(State state) {
        return new Event(
                String.format("%s is nearby. Do you want to stop for coffee?",
                        COFFEE_SHOP[coffeeShopIndex]),
                List.of(
                        "1. Yes, sure. (-cash, +coffee)",
                        "2. No, thanks."),
                List.of(
                        () -> {state.coffee += 6; state.cash -= 500;},
                        () -> {}));
    }

    private Event celebrityEncounterEvent(State state) {
        return new Event(
                String.format("%s is on the train. Do you want to pitch your product?",
                        CELEBRITY_NAME[celebrityNameIndex]),
                List.of(
                        "1. Yes, we'll give him a presentation. (-laptopBattery, -coffee, +users)",
                        "2. Yes, we'll just talk about it. (-coffee, +users)",
                        "2. No, we're shy. (-teamMorale)"),
                List.of(
                        () -> {state.laptopBattery -= 20; state.coffee -= 2; state.dailyActiveUsers += 20;},
                        () -> {state.coffee -= 4; state.dailyActiveUsers += 10;},
                        () -> {state.teamMorale -= 15;}));
    }

    private Event freeTicketEvent(State state) {
        return new Event(
                String.format("You won free passes for %s. Do you want to go?",
                        CONFERENCE_TITLE[conferenceTitleIndex]),
                List.of(
                        "1. Yes, snacks and coffee provided! (+teamMorale, -laptopBattery)",
                        "2. No, no time to spare. (-teamMorale)"),
                List.of(
                        () -> {state.laptopBattery -= 15; state.teamMorale += 20;},
                        () -> {state.teamMorale -= 5;}));
    }
}
