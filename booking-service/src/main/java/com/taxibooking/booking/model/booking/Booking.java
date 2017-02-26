package com.taxibooking.booking.model.booking;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.taxibooking.booking.model.Account;
import com.taxibooking.booking.model.Route;
import com.taxibooking.booking.model.mapping.JpaBookingStateDataConverter;
import com.taxibooking.booking.model.mapping.JsonBookingStateDataConverter;
import com.taxibooking.booking.model.taxi.Taxi;

/**
 * Represents Taxi booking.
 *
 * @author vinodkandula
 */
@Entity
@Table(name = "BOOKING")
public class Booking implements Serializable {

    @Transient
    private static final long serialVersionUID = -1373406783231928690L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIMESTAMP")
    private Date timestamp;

    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "NUMBER_PASSENGERS")
    private int numberPassengers;

    @Column(name = "COST")
    private double cost;

    @ManyToOne
    @JoinColumn(name = "PASSENGER_USERNAME")
    private Account passenger;

    @ManyToOne
    @JoinColumn(name = "TAXI_ID")
    private Taxi taxi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROUTE_ID")
    private Route route;

    @Column(name = "BOOKING_STATE")
    @Convert(converter = JpaBookingStateDataConverter.class)
    private BookingState state;

    // booking states
    @JsonIgnore
    @Transient
    private static BookingState awaitingTaxiBookingState = new AwaitingTaxiBookingState();
    @JsonIgnore
    @Transient
    private static BookingState cancelledBookingState = new CancelledBookingState();
    @JsonIgnore
    @Transient
    private static BookingState taxiDispatchedBookingState = new TaxiDispatchedBookingState();
    @JsonIgnore
    @Transient
    private static BookingState passengerPickedUpBookingState = new PassengerPickedUpBookingState();
    @JsonIgnore
    @Transient
    private static BookingState completedTaxiBookingState = new CompletedBookingState();

    public Booking() {
        // Empty constructor required by JPA.
    }

    /**
     * Constructor for class booking.
     *
     * @param passenger passenger for order.
     * @param route proposed route for taxi.
     * @param numberPassengers number passengers for booking
     */
    public Booking(Account passenger, Route route, int numberPassengers) {
        this.passenger = passenger;
        this.route = route;
        this.numberPassengers = numberPassengers;
        this.timestamp = new Date();
        this.state = Booking.getAwaitingTaxiBookingState();
        this.cost = this.estimateCost(0.3);
    }

    /**
     * Constructor for instantiating a booking class and immediately dispatching
     * a taxi.
     *
     * @param passenger passenger for order.
     * @param route proposed route for taxi.
     * @param numberPassengers number passengers for booking
     * @param taxi taxi for booking.
     */
    public Booking(Account passenger, Route route, int numberPassengers, Taxi taxi) {
        this.passenger = passenger;
        this.route = route;
        this.numberPassengers = numberPassengers;
        this.timestamp = new Date();
        this.taxi = taxi;
        this.state = Booking.getTaxiDispatchedBookingState();
        this.cost = this.calculateCost();
    }

    /**
     * Cancel booking.
     *
     * @throws IllegalStateException unable to 
     * perform state transition due to invalid state.
     */
    public void cancelBooking() {
        try {
            this.state.cancelBooking(this);
        } catch (IllegalStateException ex) {
            throw ex;
        }
    }

    /**
     * Cancel taxi.
     *
     * @throws IllegalStateException unable to 
     * perform state transition due to invalid state.
     */
    public void cancelTaxi() {

        try {
            this.state.cancelTaxi(this);
        } catch (IllegalStateException ex) {
            throw ex;
        }
    }

    /**
     * Dispatch taxi for booking.
     *
     * @param taxi the taxi to be dispatched.
     * @throws IllegalStateException unable to 
     * perform state transition due to invalid state.
     */
    public void dispatchTaxi(Taxi taxi) {

        try {
            this.state.dispatchTaxi(this, taxi);
        } catch (IllegalStateException ex) {
            throw ex;
        }
    }

    /**
     * Drop of passenger.
     *
     * @param time time passenger dropped off.
     * @throws IllegalStateException unable to 
     * perform state transition due to invalid state.
     */
    public void dropOffPassenger(Date time) {

        try {
            this.state.dropOffPassenger(this, time);
        } catch (IllegalStateException ex) {
            throw ex;
        }
    }

    /**
     * Pickup passenger.
     *
     * @param time time passenger picked up.
     * @throws IllegalStateException unable to 
     * perform state transition due to invalid state.
     */
    public void pickupPassenger(Date time) {
        try {
            this.state.pickupPassenger(this, time);
        } catch (IllegalStateException ex) {
            throw ex;
        }
    }

    /**
     * Calculate cost of taxi using taxi cost per mile.
     *
     * @return cost of taxi booking.
     */
    public final double calculateCost() {
        return this.roundToTwoDecimalPlaces(this.estimateCost(this.taxi.getCostPerMile()));
    }

    /**
     * Estimate cost for taxi using set
     *
     * @param scalar cost per mile.
     * @return estimate of taxi cost using a fixed cost per mile.
     */
    public final double estimateCost(double scalar) {
        return this.roundToTwoDecimalPlaces(scalar * this.route.getDistanceInMiles() * this.route.getTimeInMinutes());
    }

    /**
     * @return id of booking.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Set id.
     *
     * @param id id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * @return the taxi
     */
    public Taxi getTaxi() {
        return taxi;
    }

    /**
     * @param taxi the taxi to set
     */
    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
        this.cost = this.calculateCost();
    }

    /**
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * @return the passenger
     */
    public Account getPassenger() {
        return passenger;
    }

    /**
     * @param passenger the passenger to set
     */
    public void setPassenger(Account passenger) {
        this.passenger = passenger;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the state
     */
    @JsonSerialize(using = JsonBookingStateDataConverter.class)
    public BookingState getState() {
        return state;
    }
    
    /**
     * Return true if booking is active else false.
     * @return true if booking is active, else false.
     */
    @JsonIgnore
    public boolean isActive(){
        return !(this.getState() instanceof CompletedBookingState 
                || this.getState() instanceof CancelledBookingState);
    }

    /**
     * Round to two decimal places.
     *
     * @param value value to round.
     * @return the rounded value.
     */
    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * @param state the state to set
     */
    public void setState(BookingState state) {
        this.state = state;
    }

    public static BookingState getCancelledBookingState() {
        return Booking.cancelledBookingState;
    }

    public static final BookingState getAwaitingTaxiBookingState() {
        return Booking.awaitingTaxiBookingState;
    }

    public static final BookingState getTaxiDispatchedBookingState() {
        return Booking.taxiDispatchedBookingState;
    }

    public static BookingState getPassengerPickedUpBookingState() {
        return Booking.passengerPickedUpBookingState;
    }

    public static BookingState getCompletedTaxiBookingState() {
        return Booking.completedTaxiBookingState;
    }
}
