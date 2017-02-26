package com.taxibooking.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represents a Taxi route.
 *
 * @author vinodkandula
 */
@Entity
@Table(name = "ROUTE")
public class Route implements Serializable {
    
    @Transient
    private static final long serialVersionUID = -1765861964246272848L;
    
    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JoinColumn(name = "START_LOCATION")
    @ManyToOne(cascade = CascadeType.ALL)
    private Address startAddress;

    @JoinColumn(name = "END_LOCATION")
    @ManyToOne(cascade = CascadeType.ALL)
    private Address endAddress;

    @Column(name = "DISTANCE")
    private double distance;

    @Column(name = "ESTIMATED_TRAVEL_TIME")
    private double estimateTravelTime;

    @Transient
    private List<Location> path;

    public Route() {
        // Empty constructor required by JPA.
    }

    /**
     * Constructor for class Route.
     *
     * @param startAddress start location of route.
     * @param endAddress end location of route.
     * @param distance route distance in meters.
     * @param path recommended travel route.
     * @param estimateTravelTime estimated travel time between start and end
     * location in seconds.
     */
    public Route(Address startAddress, Address endAddress,
            double distance, List<Location> path, double estimateTravelTime) {

        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.distance = distance;
        this.estimateTravelTime = estimateTravelTime;
        this.path = path;
    }

    /**
     * @return the startAddress
     */
    public Address getStartAddress() {
        return this.startAddress;
    }

    /**
     * @param startAddress the startAddress to set
     */
    public void setStartLocation(Address startAddress) {
        this.startAddress = startAddress;
    }

    /**
     * @return the endAddress
     */
    public Address getEndAddress() {
        return this.endAddress;
    }

    /**
     * @param endAddress the endAddress to set
     */
    public void setEndAddress(Address endAddress) {
        this.endAddress = endAddress;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return the route
     */
    public List<Location> getPath() {
        return this.path;
    }

    /**
     * @param path the route to set
     */
    public void setRoute(List<Location> path) {
        this.path = path;
    }

    /**
     * @return the estimateTravelTime
     */
    public double getEstimateTravelTime() {
        return this.estimateTravelTime;
    }

    /**
     * @param estimateTravelTime the estimateTravelTime to set
     */
    public void setEstimateTravelTime(double estimateTravelTime) {
        this.estimateTravelTime = estimateTravelTime;
    }

    /**
     * Return distance in miles.
     *
     * @return distance in miles.
     */
    @JsonIgnore
    public double getDistanceInMiles() {
        return this.distance / 1609;
    }

    /**
     * Return time in minutes.
     *
     * @return time in minutes.
     */
    @JsonIgnore
    public double getTimeInMinutes() {
        return this.estimateTravelTime / 60;
    }
}
