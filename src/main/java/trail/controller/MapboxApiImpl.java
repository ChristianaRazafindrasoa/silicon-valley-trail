package trail.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import trail.model.MapboxApi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class MapboxApiImpl implements MapboxApi {
    private final HttpClient client;
    private final String accessToken;

    public MapboxApiImpl(HttpClient client, String accessToken) {
        this.client = client;
        this.accessToken = accessToken;
    }

    @Override
    public MapboxApiSearchResponse search(MapboxApiSearchRequest request) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalStateException();
        }
        MapboxApiSearchResponse response = new MapboxApiSearchResponse(new ArrayList<>());
        String url = String.format(
                "https://api.mapbox.com/search/searchbox/v1/forward?q=%s" +
                        "&proximity=%s,%s&limit=3&access_token=%s",
                request.query(),
                request.lat(),
                request.lon(),
                accessToken);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(
                    httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() <=299) {
                response = parse(httpResponse.body());
            }
        } catch (IOException | InterruptedException _) {

        }
        return response;
    }

    private MapboxApiSearchResponse parse(String json) {
        JsonArray features = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonArray("features");

        MapboxApiSearchResponse response = new MapboxApiSearchResponse(new ArrayList<>());
        for (JsonElement element : features) {
            JsonObject properties = element.getAsJsonObject()
                    .getAsJsonObject("properties");
            String name = properties.get("name")
                    .toString()
                    .replace("\"", "");
            double latitude = properties.getAsJsonObject("coordinates")
                    .get("latitude")
                    .getAsDouble();
            double longitude = properties.getAsJsonObject("coordinates")
                    .get("longitude")
                    .getAsDouble();
            response.entries().add(new MapboxApiSearchEntry(name, latitude, longitude));
        }
        return response;
    }
}