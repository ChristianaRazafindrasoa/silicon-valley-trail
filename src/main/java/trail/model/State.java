package trail.model;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class State {
    public static final City[] CITIES = new City[]{
            new City("San Jose", -121.8863, 37.3382),
            new City("Mountain View", -122.0838, 37.3861),
            new City("San Mateo", -122.3255, 37.5630),
            new City("San Francisco", -122.4194, 37.7749)};
    //    public static final City[] CITIES = new City[]{
//            new City("San Jose", -121.8863, 37.3382),
//            new City("Santa Clara", -121.9552, 37.3541),
//            new City("Sunnyvale", -122.0363, 37.3688),
//            new City("Mountain View", -122.0838, 37.3861),
//            new City("Palo Alto", -122.1430, 37.4419),
//            new City("Redwood City", -122.2364, 37.4852),
//            new City("San Mateo", -122.3255, 37.5630),
//            new City("Millbrae", -122.3872, 37.5985),
//            new City("South San Francisco", -122.4194, 37.6547),
//            new City("Daly City", -122.4702, 37.6879),
//            new City("San Francisco", -122.4194, 37.7749)};

        @Expose
        public int cityIndex;
        @Expose
        public int coffee;
        @Expose
        public int cash;
        @Expose
        public int laptopBattery;
        @Expose
        public int teamMorale;
        @Expose
        public int dailyActiveUsers;
        @Expose
        public int day = 1;

    public State(int coffee, int cash, int laptopBattery, int teamMorale, int dailyActiveUsers) {
        this.coffee = coffee;
        this.cash = cash;
        this.laptopBattery = laptopBattery;
        this.teamMorale = teamMorale;
        this.dailyActiveUsers = dailyActiveUsers;
    }

    public City getCurrentCity() {
        if (cityIndex >= CITIES.length) {
            return null;
        }
        return CITIES[cityIndex];
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return cityIndex == state.cityIndex &&
                coffee == state.coffee &&
                cash == state.cash &&
                laptopBattery == state.laptopBattery &&
                teamMorale == state.teamMorale &&
                dailyActiveUsers == state.dailyActiveUsers &&
                day == state.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityIndex, coffee, cash, laptopBattery, teamMorale, dailyActiveUsers, day);
    }
}
