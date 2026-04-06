package trail.model;

import java.util.Objects;

public class City {
    private final String name;
    private final double latitude;
    private final double longitude;

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Double.compare(latitude, city.latitude) == 0 &&
                Double.compare(longitude, city.longitude) == 0 &&
                Objects.equals(name, city.name);
    }
}
