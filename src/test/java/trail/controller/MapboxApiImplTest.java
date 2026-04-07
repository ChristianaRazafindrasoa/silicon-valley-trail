package trail.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trail.model.MapboxApi;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapboxApiImplTest {
    private final String VALID_KEY = GameController.getEnvVariable("MAPBOX_SECRET_KEY");
    private final String INVALID_KEY = "invalid-mapbox-key";

    @Test
    void shouldReturnNonEmptySearchWithValidKey() throws IOException, InterruptedException {
        MapboxApiImpl mapbox = new MapboxApiImpl(HttpClient.newHttpClient(), VALID_KEY);
        MapboxApi.MapboxApiSearchRequest request = new MapboxApi.MapboxApiSearchRequest(
                "coffee", -126.3754, 37.3385);
        MapboxApi.MapboxApiSearchResponse response = mapbox.search(request);
        assertNotNull(response);
        assertNotEquals(0, response.entries().size());
    }

    @Test
    void shouldReturnEmptySearchWithInvalidKey() throws IOException, InterruptedException {
        MapboxApiImpl mapbox = new MapboxApiImpl(HttpClient.newHttpClient(), INVALID_KEY);
        MapboxApi.MapboxApiSearchRequest request = new MapboxApi.MapboxApiSearchRequest(
                "coffee", -126.3754, 37.3385);
        MapboxApi.MapboxApiSearchResponse response = mapbox.search(request);
        assertNotNull(response);
        assertEquals(0, response.entries().size());
    }
}