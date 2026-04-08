package trail.controller;

import org.junit.jupiter.api.Test;
import trail.model.MapboxApi;

import java.net.http.HttpClient;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MapboxApiImplTest {

    @Test
    void shouldReturnNonEmptySearchWithValidKey() {
        Optional<String> validKey = GameUtility.getEnvVariable("MAPBOX_SECRET_KEY");
        if (validKey.isEmpty()) {
            System.out.println("Skipping test due to missing Mapbox key.");
            return;
        }
        MapboxApiImpl mapbox = new MapboxApiImpl(HttpClient.newHttpClient(), validKey.get());
        MapboxApi.MapboxApiSearchRequest request = new MapboxApi.MapboxApiSearchRequest(
                "coffee", -126.3754, 37.3385);
        MapboxApi.MapboxApiSearchResponse response = mapbox.search(request);
        assertNotNull(response);
        assertFalse(response.entries().isEmpty());
    }

    @Test
    void shouldReturnEmptySearchWithInvalidKey() {
        String invalidKey = "invalid-mapbox-key";
        MapboxApiImpl mapbox = new MapboxApiImpl(HttpClient.newHttpClient(), invalidKey);
        MapboxApi.MapboxApiSearchRequest request = new MapboxApi.MapboxApiSearchRequest(
                "coffee", -126.3754, 37.3385);
        MapboxApi.MapboxApiSearchResponse response = mapbox.search(request);
        assertNotNull(response);
        assertEquals(0, response.entries().size());
    }

    @Test
    void shouldThrowExceptionWithNullKey() {
        MapboxApiImpl mapbox = new MapboxApiImpl(HttpClient.newHttpClient(), null);
        MapboxApi.MapboxApiSearchRequest request = new MapboxApi.MapboxApiSearchRequest(
                "coffee", -126.3754, 37.3385);
        assertThrows(IllegalStateException.class, () -> mapbox.search(request));
    }
}