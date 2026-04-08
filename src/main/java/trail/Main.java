package trail;

import trail.controller.*;
import trail.view.ConsoleIO;

import java.net.http.HttpClient;
import java.util.Random;

public class Main {

    static void main() {
        String mapboxKey = GameUtility.getEnvVariable("MAPBOX_SECRET_KEY").orElse(null);
        if (mapboxKey == null) {
            System.out.println("Warning: Mapbox secret key not set up. Running on mock data.");
        }

        ConsoleIO io = new ConsoleIO();
        Random random = new Random();
        MapboxApiImpl mapbox = new MapboxApiImpl(HttpClient.newHttpClient(), mapboxKey);
        GameEvents gameEvents = new GameEvents(mapbox, random);
        GameLoader gameLoader = new GameLoader(GameConstants.STATE_FILE_PATH);

        GameController gameController = new GameController(io, gameEvents, gameLoader);
        gameController.run();
    }

}
