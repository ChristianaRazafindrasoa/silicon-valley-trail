package trail.controller;

import trail.model.City;
import trail.model.State;

public class GameConstants {
    public static final String STATE_FILE_PATH = "./state.json";
    public static final String ENV_FILE_PATH = ".env";
    public static final int STARTING_COFFEE = 8;
    public static final int STARTING_CASH = 5000;
    public static final int STARTING_BATTERY = 100;
    public static final int STARTING_USERS = 20;
    public static final int STARTING_MORALE = 90;
    public static final int STARTING_DAY = 1;

    public static final City[] CITIES = new City[]{
            new City("San Jose", -121.8863, 37.3382),
            new City("Santa Clara", -121.9552, 37.3541),
            new City("Sunnyvale", -122.0363, 37.3688),
            new City("Mountain View", -122.0838, 37.3861),
            new City("Palo Alto", -122.1430, 37.4419),
            new City("Redwood City", -122.2364, 37.4852),
            new City("San Mateo", -122.3255, 37.5630),
            new City("Millbrae", -122.3872, 37.5985),
            new City("South San Francisco", -122.4194, 37.6547),
            new City("San Francisco", -122.4194, 37.7749)};
    public static final String[] COFFEE_SHOPS = new String[]{
            "Starbucks", "Dunkin'", "Dutch Bros"};
    public static final String[] CELEBRITY_NAMES = new String[]{
            "Ryan Roslansky", "Tim Cook", "Jeff Bezos"};
    public static final String[] CONFERENCE_TITLES = new String[]{
            "GitHub Universe", "Stanford TreeHacks", "Nvidia GTC"};
    public static final String[] TECH_FAILURES = new String[]{
            "major bug", "server crash", "data leaked"};

    public static State buildStartingState() {
        return new State(
                STARTING_COFFEE, STARTING_CASH, STARTING_BATTERY,
                STARTING_MORALE, STARTING_USERS);
    }
}
