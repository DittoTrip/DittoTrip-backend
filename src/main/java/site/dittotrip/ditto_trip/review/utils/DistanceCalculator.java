package site.dittotrip.ditto_trip.review.utils;

import site.dittotrip.ditto_trip.spot.domain.Spot;

public class DistanceCalculator {

    /**
     * params : two point(x, y)
     * return distance (meter)
     */
    public static double getDistanceBetweenUserAndSpot(double x1, double y1, double x2, double y2) {
        double theta = x1 - x2;
        double dist = Math.sin(deg2rad(y1)) * Math.sin(deg2rad(y2)) +
                Math.cos(deg2rad(y1)) * Math.cos(deg2rad(y2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1609.344;
        return dist;
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
