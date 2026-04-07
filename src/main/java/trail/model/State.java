package trail.model;

import com.google.gson.annotations.Expose;

public class State {
//    public static final City[] CITIES = new City[]{
//            new City("San Jose", -121.8863, 37.3382),
//            new City("Mountain View", -122.0838, 37.3861),
//            new City("San Mateo", -122.3255, 37.5630),
//            new City("San Francisco", -122.4194, 37.7749)};
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
        new City("Daly City", -122.4702, 37.6879),
        new City("San Francisco", -122.4194, 37.7749)};

        @Expose
        private int cityIndex;
        @Expose
        private int coffee;
        @Expose
        private int cash;
        @Expose
        private int laptopBattery;
        @Expose
        private int teamMorale;
        @Expose
        private int dailyActiveUsers;
        @Expose
        private int day = 1;

    public State(int coffee, int cash, int laptopBattery, int teamMorale, int dailyActiveUsers) {
        this.coffee = coffee;
        this.cash = cash;
        this.laptopBattery = laptopBattery;
        this.teamMorale = teamMorale;
        this.dailyActiveUsers = dailyActiveUsers;
    }

    public City getCurrentCity() {
        if (cityIndex >= CITIES.length) {
            throw new IllegalStateException();
        }
        return CITIES[cityIndex];
    }

    public boolean hasNextCity() {
        return cityIndex < CITIES.length - 1;
    }

    public void getNextCity() {
        if (!hasNextCity()) {
            throw new IllegalStateException();
        }
        cityIndex++;
    }

    public int getProgressToSf() {
        City first = CITIES[0];
        City current = getCurrentCity();
        City last = CITIES[CITIES.length - 1];
        double distanceTravelled = current.getDistance(
                first.getLatitude(), first.getLongitude());
        double fullDistance = first.getDistance(
                last.getLatitude(), last.getLongitude());
        return (int) (distanceTravelled / fullDistance * 100);
    }

    public void getNextDay() {
        day++;
    }

    public void adjustCoffee(int coffee) {
        this.coffee = Math.max(0, this.coffee + coffee);
    }

    public void adjustMorale(int teamMorale) {
        this.teamMorale = Math.min(100, this.teamMorale + teamMorale);
    }

    public void adjustBattery(int battery) {
        this.laptopBattery = Math.min(100, this.laptopBattery + battery);
    }

    public void adjustCash(int cash) {
        this.cash += cash;
    }

    public void adjustUsers(int newUsers) {
        this.dailyActiveUsers += newUsers;
    }

    public boolean didLaptopDie() {
        return laptopBattery <= 0;
    }

    public boolean didTeamQuit() {
        return teamMorale <= 0;
    }

    public boolean didCoffeeRunOut() {
        return coffee <= 0;
    }

    public boolean didCashRunOut() {
        return cash <= 0;
    }

    public boolean notEnoughUsers() {
        return dailyActiveUsers < 200;
    }

    public int getCoffee() { return coffee; }
    public int getCash() { return cash;}
    public int getBattery() { return laptopBattery; }
    public int getMorale() { return teamMorale; }
    public int getUsers() { return dailyActiveUsers; }
    public int getDay() { return day; }

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
}
