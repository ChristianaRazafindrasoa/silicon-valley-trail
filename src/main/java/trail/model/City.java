package trail.model;

import java.util.Objects;

public class City {
    private static final double EARTH_RADIUS_MILES = 3958.8;
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

    public double getDistance(double latitude, double longitude) {
        double dLat = Math.toRadians(this.latitude - latitude);
        double dLon = Math.toRadians(this.longitude - longitude);
        double lat1Rad = Math.toRadians(latitude);
        double lat2Rad = Math.toRadians(this.latitude);
        return EARTH_RADIUS_MILES * 2 * Math.asin(Math.sqrt(
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        ));
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
