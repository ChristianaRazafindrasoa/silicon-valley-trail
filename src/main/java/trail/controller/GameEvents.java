package trail.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import trail.model.City;
import trail.model.Event;
import trail.model.State;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEvents {
    private static final String FILE_PATH = ".env";
    private static final String[] COFFEE_SHOP = new String[]{
            "Starbucks", "Philz Coffee", "Voyager Coffee", "Blue Bottle"};
    private static final String[] CELEBRITY_NAME = new String[]{
            "Ryan Roslansky", "Tim Cook", "Sam Altman", "Jeff Bezos"};
    private static final String[] CONFERENCE_TITLE = new String[]{
            "Stanford TreeHacks", "GitHub Universe", "Nvidia GTC", "TECHSPO"};
    private static final String[] WEATHER_TYPE = new String[]{"Clear", "Foggy"};
    private int coffeeShopIndex = 0;
    private int celebrityNameIndex = 0;
    private int conferenceTitleIndex = 0;

    public Event getRandomEvent(Random random, State state) throws IOException {
        Event event = null;
        int rand = random.nextInt(3);
        if (rand == 1) {
            event = coffeeShopEvent(state);
        } else if (rand == 2) {
            event = celebrityEncounterEvent(state);
        } else {
            event = freeTicketEvent(state);
        }
        return event;
    }

    private Event coffeeShopEvent(State state) throws IOException {
        Event event = null;
        City currentCity = state.getCurrentCity();
        String longitude = Double.toString(currentCity.getLongitude());
        String latitude = Double.toString(currentCity.getLatitude());
        String accessToken = getEnvVariable("MAPBOX_SECRET_KEY");
        String url = String.format(
                "https://api.mapbox.com/search/searchbox/v1/forward?q=coffee" +
                        "&proximity=%s,%s&limit=3&access_token=%s",
                        latitude, longitude, accessToken);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            event = buildCoffeeShopEvent(state, response.body());
        } catch (IOException | InterruptedException e) {
            event = fallbackCoffeeShopEvent(state);
        }
        return event;
    }

    private Event buildCoffeeShopEvent(State state, String json) {
        JsonArray features = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonArray("features");

        List<String> options = new ArrayList<>();
        int index = 1;
        for (JsonElement element : features) {
            String name = element.getAsJsonObject()
                    .getAsJsonObject("properties")
                    .get("name").toString();
            String option = String.format("%d. %s (-cash, +coffee)", index++, name);
            options.add(option);
        }
        options.add(String.format("%d. No, thanks", index));

        return new Event("Three coffee shops found nearby. Do you want to stop for coffee?",
                options,
                List.of(
                        () -> {state.coffee += 6; state.cash -= 800;},
                        () -> {state.coffee += 6; state.cash -= 300;},
                        () -> {state.coffee += 6; state.cash -= 1000;},
                        () -> {}));
    }

    private Event fallbackCoffeeShopEvent(State state) {
        Event event = new Event(
                String.format("%s is nearby. Do you want to stop for coffee?",
                        COFFEE_SHOP[coffeeShopIndex]),
                List.of(
                        "1. Yes, sure. (-cash, +coffee)",
                        "2. No, thanks."),
                List.of(
                        () -> {state.coffee += 6; state.cash -= 500;},
                        () -> {}));
        coffeeShopIndex = (coffeeShopIndex + 1) % COFFEE_SHOP.length;
        return event;
    }

    private Event celebrityEncounterEvent(State state) {
        Event event = new Event(
                String.format("%s is at the train station. Do you want to pitch your product?",
                        CELEBRITY_NAME[celebrityNameIndex]),
                List.of(
                        "1. Yes, we'll give him a presentation. (-laptopBattery, -coffee, +users)",
                        "2. Yes, we'll just talk about it. (-coffee, +users)",
                        "2. No, we're shy. (-teamMorale)"),
                List.of(
                        () -> {state.laptopBattery -= 20; state.coffee -= 2; state.dailyActiveUsers += 20;},
                        () -> {state.coffee -= 4; state.dailyActiveUsers += 10;},
                        () -> {state.teamMorale -= 15;}));
        celebrityNameIndex = (celebrityNameIndex + 1) % CELEBRITY_NAME.length;
        return event;
    }

    private Event freeTicketEvent(State state) {
        Event event = new Event(
                String.format("You won free passes for %s. Do you want to go?",
                        CONFERENCE_TITLE[conferenceTitleIndex]),
                List.of(
                        "1. Yes, snacks and coffee provided! (+teamMorale, -laptopBattery)",
                        "2. No, no time to spare. (-teamMorale)"),
                List.of(
                        () -> {state.laptopBattery -= 15; state.teamMorale += 20;},
                        () -> {state.teamMorale -= 5;}));
        conferenceTitleIndex = (conferenceTitleIndex + 1) % CONFERENCE_TITLE.length;
        return event;
    }

    private String getEnvVariable(String key) throws IOException {
        String contents = Files.readString(Path.of(FILE_PATH));
        for (String line : contents.split("\n")) {
            String[] parts = line.split("=");
            if (parts[0].equalsIgnoreCase(key)) {
                return parts[1];
            }
        }
        return null;
    }
}