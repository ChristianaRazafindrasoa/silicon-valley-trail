package trail.controller;

import org.junit.jupiter.api.Test;
import trail.model.Event;
import trail.model.MapboxApi;
import trail.model.State;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameEventsTest {

    @Test
    void shouldReturnFallbackWhenApiFails() {
        MapboxApi api = request -> {
            throw new IOException();
        };
        Random random = new Random(3);
        GameEvents gameEvents = new GameEvents(api, random);
        State state = new State(
                12, 3500, 100, 65, 76);
        Event actualEvent = gameEvents.coffeeShopEvent(state);
        Event initialFallbackEvent =
                new GameEvents(api, random).fallbackCoffeeShopEvent(state);
        assertEquals(actualEvent, initialFallbackEvent);
    }

    @Test
    void shouldReturnFallbackWhenApiReturnsEmpty() {
        MapboxApi api = request ->
                new MapboxApi.MapboxApiSearchResponse(List.of());
        Random random = new Random(3);
        GameEvents gameEvents = new GameEvents(api, random);
        State state = new State(
                12, 3500, 100, 65, 76);
        Event actualEvent = gameEvents.coffeeShopEvent(state);
        Event initialFallbackEvent =
                new GameEvents(api, random).fallbackCoffeeShopEvent(state);
        assertEquals(actualEvent, initialFallbackEvent);
    }

    @Test
    void shouldReturnEventWhenApiReturnsNonEmpty() {
        MapboxApi api = request ->
                new MapboxApi.MapboxApiSearchResponse(List.of(
                        new MapboxApi.MapboxApiSearchEntry("Dutch Bros", -121.32, 92.31)));
        Random random = new Random(3);
        GameEvents gameEvents = new GameEvents(api, random);
        State state = new State(
                12, 3500, 100, 65, 76);
        Event event = gameEvents.coffeeShopEvent(state);
        assertNotNull(event);
        assertEquals(2, event.options().size());
        assertTrue(event.options().get(0).contains("Dutch Bros"));
    }
}