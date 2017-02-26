package com.taxibooking.booking.rest;

import java.io.Serializable;

import com.taxibooking.booking.model.Location;

/**
 * Booking data transfer object to reduce data coupling on methods. Martin
 * Fowler discussion on data access objects.
 * http://martinfowler.com/eaaCatalog/dataTransferObject.html
 *
 * @author vinodkandula
 */
public class BookingDto implements Serializable {

    private long id;
    private String passengerUsername;
    private Location startLocation;
    private Location endLocation;
    private int numberPassengers;

    /**
     * @return the startLocation
     */
    public Location getStartLocation() {
        return startLocation;
    }

    /**
     * @param startLocation the startLocation to set
     */
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * @return the endLocation
     */
    public Location getEndLocation() {
        return endLocation;
    }

    /**
     * @param endLocation the endLocation to set
     */
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    /**
     * @return the passengeUsername
     */
    public String getPassengerUsername() {
        return passengerUsername;
    }

    /**
     * @param passengeUsername the passengeUusername to set
     */
    public void setPassengerUsername(String passengeUsername) {
        this.passengerUsername = passengeUsername;
    }

    /**
     * @return the numberPassengers
     */
    public int getNumberPassengers() {
        return numberPassengers;
    }

    /**
     * @param numberPassengers the numberPassengers to set
     */
    public void setNumberPassengers(int numberPassengers) {
        this.numberPassengers = numberPassengers;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
}
