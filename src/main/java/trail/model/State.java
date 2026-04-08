package trail.model;

import com.google.gson.annotations.Expose;
import trail.controller.GameConstants;

public class State {
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
        private int day = GameConstants.STARTING_DAY;
        @Expose
        private int consecutiveDaysWithoutCoffee = 0;

    public State(int coffee, int cash, int laptopBattery, int teamMorale, int dailyActiveUsers) {
        this.coffee = coffee;
        this.cash = cash;
        this.laptopBattery = laptopBattery;
        this.teamMorale = teamMorale;
        this.dailyActiveUsers = dailyActiveUsers;
    }

    public City getCurrentCity() {
        if (cityIndex >= GameConstants.CITIES.length) {
            throw new IllegalStateException();
        }
        return GameConstants.CITIES[cityIndex];
    }

    public boolean hasNextCity() {
        return cityIndex < GameConstants.CITIES.length - 1;
    }

    public void getNextCity() {
        if (!hasNextCity()) {
            throw new IllegalStateException();
        }
        cityIndex++;
    }

    public int getProgressToSf() {
        City first = GameConstants.CITIES[0];
        City current = getCurrentCity();
        City last = GameConstants.CITIES[GameConstants.CITIES.length - 1];
        double distanceTravelled = current.getDistance(
                first.latitude(), first.longitude());
        double totalDistance = first.getDistance(
                last.latitude(), last.longitude());
        return (int) (distanceTravelled / totalDistance * 100);
    }

    public void incrementDay() { day++; }

    public int getNextConsecutiveDays() { return consecutiveDaysWithoutCoffee++; }

    public void resetConsecutiveDays() { consecutiveDaysWithoutCoffee = 0; }

    public void adjustCoffee(int coffee) {
        this.coffee = Math.max(0, this.coffee + coffee);
    }
    public void adjustMorale(int teamMorale) {
        if (teamMorale < 0 && consecutiveDaysWithoutCoffee >= 2) {
            teamMorale *= 2;
        }
        this.teamMorale = Math.min(100, this.teamMorale + teamMorale);
    }
    public void adjustBattery(int battery) {
        this.laptopBattery = Math.min(100, this.laptopBattery + battery);
    }
    public void adjustCash(int cash) {
        this.cash = Math.max(0, this.cash + cash);
    }
    public void adjustUsers(int newUsers) {
        dailyActiveUsers = Math.max(0, dailyActiveUsers + newUsers);
    }

    public boolean didLaptopDie() {
        return laptopBattery <= 0;
    }
    public boolean didTeamQuit() { return teamMorale <= 0; }
    public boolean didCoffeeRunOut() { return coffee <= 0; }
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
