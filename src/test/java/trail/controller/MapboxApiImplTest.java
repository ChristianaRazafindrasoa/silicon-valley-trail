package trail.controller;

import org.junit.jupiter.api.Test;
import trail.model.MapboxApi;

import java.net.http.HttpClient;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MapboxApiImplTest {

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