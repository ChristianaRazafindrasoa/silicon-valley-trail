package trail.model;

public record City(String name, double latitude, double longitude) {
    private static final double EARTH_RADIUS_MILES = 3958.8;
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
}
