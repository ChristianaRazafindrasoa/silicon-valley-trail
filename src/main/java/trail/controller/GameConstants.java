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
            new City("San Jose", 37.3382, -121.8863),
            new City("Santa Clara", 37.3541, -121.9552),
            new City("Sunnyvale", 37.3688, -122.0363),
            new City("Mountain View", 37.3861, -122.0838),
            new City("Palo Alto", 37.4419, -122.1430),
            new City("Redwood City", 37.4852, -122.2364),
            new City("San Mateo", 37.5630, -122.3255),
            new City("Millbrae", 37.5985, -122.3872),
            new City("South San Francisco", 37.6547, -122.4194),
            new City("San Francisco", 37.7749, -122.4194)};

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
