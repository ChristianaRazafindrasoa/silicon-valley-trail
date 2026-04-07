package trail.controller;

import org.junit.jupiter.api.Test;
import trail.model.Event;
import trail.model.MapboxApi;
import trail.model.State;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameEventsTest {
    private final String VALID_KEY = GameController.getEnvVariable("MAPBOX_SECRET_KEY");
    MapboxApiImpl mapbox = new MapboxApiImpl(HttpClient.newHttpClient(), VALID_KEY);
    GameEvents gameEvents = new GameEvents(mapbox);

    @Test
    void shouldGetRandomEvent() {
        State state = new State(
                12, 3500, 0, 65, 76);
        Event event = gameEvents.getRandomEvent(new Random(), state);
        assertNotNull(event);
    }

    @Test
    void shouldGetCoffeeShopEvent() {
        State state = new State(
                12, 3500, 0, 65, 76);
        Event event = gameEvents.coffeeShopEvent(state);
        assertNotNull(event);
    }

    @Test
    void shouldBuildCoffeeShopEventWithEmptySearch() {
        State state = new State(
                12, 3500, 0, 65, 76);
        MapboxApi.MapboxApiSearchResponse response = new MapboxApi
                .MapboxApiSearchResponse(new ArrayList<>());
        Event event = gameEvents.buildCoffeeShopEvent(state, response);
        assertNotNull(event);
    }
}