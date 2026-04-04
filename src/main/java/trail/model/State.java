package trail.model;

public class State {
//    public static final String[] CITIES = new String[]{
//            "San Jose", "Santa Clara", "Sunnyvale", "Mountain View", "Palo Alto", "Redwood City",
//            "San Mateo", "Millbrae", "South San Francisco", "Daly City", "San Francisco"};
    public static final String[] CITIES = new String[]{
            "San Jose", "Mountain View", "San Mateo", "San Francisco"};
    public int cityIndex;
    public int coffee;
    public int cash;
    public int laptopBattery;
    public int teamMorale;
    public int dailyActiveUsers;
    public int day = 1;


    public State() {}

    public State(int coffee, int cash, int laptopBattery, int teamMorale, int dailyActiveUsers) {
        this.coffee = coffee;
        this.cash = cash;
        this.laptopBattery = laptopBattery;
        this.teamMorale = teamMorale;
        this.dailyActiveUsers = dailyActiveUsers;
    }



    // Travel (-coffee -cash -morale)
    // Work (-cash -coffee -battery)
    // Promote (+morale +dailyActiveUsers -cash)
    // Rest (+morale + battery)
    // Quit

}
