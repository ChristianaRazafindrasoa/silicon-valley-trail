package trail.controller;

import trail.model.City;
import trail.model.Event;
import trail.model.MapboxApi;
import trail.model.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEvents {
    private final MapboxApi mapbox;
    private final Random random;

    private int coffeeShopIndex = 0;
    private int celebrityNameIndex = 0;
    private int conferenceTitleIndex = 0;
    private int techFailuresIndex = 0;

    public GameEvents(MapboxApi mapbox, Random random) {
        this.mapbox = mapbox;
        this.random = random;
    }

    public Event getRandomEvent(State state) {
        Event event;
        int rand = random.nextInt(7);
        if (rand < 3) {
            event = coffeeShopEvent(state);
        } else if (rand == 3) {
            event = celebrityEncounterEvent(state);
        } else if (rand == 4) {
            event = freeTicketEvent(state);
        } else {
            event = techFailureEvent(state);
        }
        return event;
    }

     Event coffeeShopEvent(State state) {
        Event event;
        City currentCity = state.getCurrentCity();
        MapboxApi.MapboxApiSearchRequest request = new MapboxApi.MapboxApiSearchRequest(
                "coffee", currentCity.latitude(), currentCity.longitude());
        try {
            MapboxApi.MapboxApiSearchResponse response = mapbox.search(request);
            event = buildCoffeeShopEvent(state, response);
        } catch (Throwable throwable) {
            event = fallbackCoffeeShopEvent(state);
        }
        return event;
    }

    private Event buildCoffeeShopEvent(State state, MapboxApi.MapboxApiSearchResponse response) {
        if (response.entries() == null || response.entries().isEmpty()) {
            return fallbackCoffeeShopEvent(state);
        }
        List<String> options = new ArrayList<>(response.entries().size() + 1);
        int index = 1;
        for (MapboxApi.MapboxApiSearchEntry entry : response.entries()) {
            double distance = state.getCurrentCity()
                    .getDistance(entry.lon(), entry.lat());
            String option = String.format("%d. %s %.2f miles away (+coffee, -cash)",
                    index++, entry.name(), distance);
            options.add(option);
        }
        options.add(String.format("%d. No, thanks.", index));
        return new Event("Coffee shops found nearby. Do you want to stop for coffee?",
                options,
                List.of(
                        () -> {state.adjustCoffee(+6); state.adjustCash(-100);},
                        () -> {state.adjustCoffee(+6); state.adjustCash(-250);},
                        () -> {state.adjustCoffee(+6); state.adjustCash(-200);},
                        () -> {}));
    }

    Event fallbackCoffeeShopEvent(State state) {
        Event event = new Event(
                String.format("%s is nearby. Do you want to stop for coffee?",
                        GameConstants.COFFEE_SHOPS[coffeeShopIndex]),
                List.of(
                        "1. Yes, sure. (-cash, +coffee)",
                        "2. No, thanks."),
                List.of(
                        () -> {state.adjustCoffee(+6); state.adjustCash(-200);},
                        () -> {}));
        coffeeShopIndex = (coffeeShopIndex + 1) % GameConstants.COFFEE_SHOPS.length;
        return event;
    }

    private Event celebrityEncounterEvent(State state) {
        Event event = new Event(
                String.format("%s is at the train station. Do you want to pitch your product?",
                        GameConstants.CELEBRITY_NAMES[celebrityNameIndex]),
                List.of(
                        "1. Yes, we'll give him a presentation. (-laptopBattery, +users)",
                        "2. Yes, we'll just talk about it. (-coffee, +users)",
                        "3. No, we're shy. (-teamMorale)"),
                List.of(
                        () -> {state.adjustBattery(-20); state.adjustUsers(+20);},
                        () -> {state.adjustCoffee(-4); state.adjustUsers(+10);},
                        () -> {state.adjustMorale(-15);}));
        celebrityNameIndex = (celebrityNameIndex + 1) % GameConstants.CELEBRITY_NAMES.length;
        return event;
    }

    private Event freeTicketEvent(State state) {
        Event event = new Event(
                String.format("You won free passes for %s. Do you want to go?",
                        GameConstants.CONFERENCE_TITLES[conferenceTitleIndex]),
                List.of(
                        "1. Yes, snacks and coffee provided! (+teamMorale, -laptopBattery)",
                        "2. No, no time to spare. (-teamMorale)"),
                List.of(
                        () -> {state.adjustBattery(-15); state.adjustMorale(+20);},
                        () -> {state.adjustMorale(-10);}));
        conferenceTitleIndex = (conferenceTitleIndex + 1) % GameConstants.CONFERENCE_TITLES.length;
        return event;
    }

    private Event techFailureEvent(State state) {
        Event event = new Event(
                String.format("A %s has happened. Do you want to fix it?",
                        GameConstants.TECH_FAILURES[techFailuresIndex]),
                List.of(
                        "1. Yes, let's fix it immediately! (-laptopBattery)",
                        "2. Later, let's procrastinate. (-teamMorale)",
                        "3. No, let's ignore it for now. (-users)"),
                List.of(
                        () -> {state.adjustBattery(-30);},
                        () -> {state.adjustBattery(-10); state.adjustMorale(-20);},
                        () -> {state.adjustUsers(-15);}));
        techFailuresIndex = (techFailuresIndex + 1) % GameConstants.TECH_FAILURES.length;
        return event;
    }
}