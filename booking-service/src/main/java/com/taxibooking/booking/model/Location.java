package com.taxibooking.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represent a location by latitude and longitude.
 *
 * @author vinodkandula
 */
@Entity
@Table(name = "LOCATION")
public class Location implements Serializable {

    @Transient
    private static final long serialVersionUID = -6935663821232865289L;

    @Transient
    private static final double MIN_LATITUDE = -90;
    @Transient
    private static final double MAX_LATITUDE = 90;
    @Transient
    private static final double MIN_LONGITUDE = -180;
    @Transient
    private static final double MAX_LONGITUDE = 180;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    public Location() {
        // Empty constructor required by JPA.
    }

    /**
     * Constructor for class Location.
     *
     * @param latitude latitude.
     * @param longitude longitude.
     * @throws IllegalArgumentException latitude or longitude is invalid.
     */
    public Location(double latitude, double longitude) {

        if (!Location.validateCoordinates(latitude, longitude)) {
            throw new IllegalArgumentException("Invalid latitude or longitude.");
        }

        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Validate latitude and longitude
     *
     * @param latitude latitude.
     * @param longitude longitude.
     * @return true if coordinates valid else false.
     */
    public static boolean validateCoordinates(double latitude, double longitude) {
        return Location.validateLatitude(latitude) && Location.validateLongitude(longitude);
    }

    /**
     * Validate latitude.
     *
     * @param latitude latitude.
     * @return true if valid else false.
     */
    public static boolean validateLatitude(double latitude) {
        return !(latitude > Location.MAX_LATITUDE || latitude < Location.MIN_LATITUDE);
    }

    /**
     * Validate longitude.
     *
     * @param longitude longitude.
     * @return true if valid else false.
     */
    public static boolean validateLongitude(double longitude) {
        return !(longitude < Location.MIN_LONGITUDE || longitude > Location.MAX_LONGITUDE);
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     * @throws IllegalArgumentException latitude is not between 90 and -90.
     */
    public void setLatitude(double latitude) {

        if (Location.validateLatitude(latitude)) {
            this.latitude = latitude;
        } else {
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        }
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     * @throws IllegalArgumentException longitude is not between 90 and -90.
     */
    public void setLongitude(double longitude) {
        if (Location.validateLatitude(longitude)) {
            this.longitude = longitude;
        } else {
            throw new IllegalArgumentException("Latitude must be between -180 and 180.");
        }
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Return distance in meters between start and end location using Haversine
     * formula.
     * http://stackoverflow.com/questions/120283/how-can-i-measure-distance-and-create-a-bounding-box-based-on-two-latitudelongi
     *
     * @param startLocation start location.
     * @param endLocation end location.
     * @return distance in meters between start and end location.
     * @throws IllegalArgumentException if start or end location is null.
     */
    public static double getDistance(Location startLocation, Location endLocation) {

        if (startLocation == null || endLocation == null) {
            throw new IllegalArgumentException("Start and end location cannot be null.");
        }

        double lat1 = startLocation.getLatitude();
        double lat2 = endLocation.getLatitude();
        double lng1 = startLocation.getLongitude();
        double lng2 = endLocation.getLongitude();

        double earthRadius = 6371000;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    /**
     * Convert to maindenhead head grid reference. 
     * Code reference: https://github.com/Smerty/jham/blob/master/src/main/java/org/smerty/jham/Location.java
     * @param latitudeIn latitude.
     * @param longitudeIn longitude.
     * @return a string representation of the maidenhead grid reference for the latitude and longitude.
     */
    public static String toMaidenhead(final double latitudeIn,
            final double longitudeIn) {

        double longitude = longitudeIn + 180;
        longitude /= 2;
        char lonFirst = (char) ('A' + (longitude / 10));
        char lonSecond = (char) ('0' + longitude % 10);
        char lonThird = (char) ('A' + (longitude % 1) * 24);

        double latitude = latitudeIn + 90;
        char latFirst = (char) ('A' + (latitude / 10));
        char latSecond = (char) ('0' + latitude % 10);
        char latThird = (char) ('A' + (latitude % 1) * 24);

        StringBuilder sb = new StringBuilder();
        sb.append(lonFirst);
        sb.append(latFirst);
        sb.append(lonSecond);
        sb.append(latSecond);
        sb.append(("" + lonThird).toLowerCase());
        sb.append(("" + latThird).toLowerCase());

        return sb.toString();
    }

    @Override
    public String toString() {
        return this.latitude + "," + this.longitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        return true;
    }
}
