package com.taxibooking.booking.model.booking;

import java.io.Serializable;
import java.util.Date;

import com.taxibooking.booking.model.taxi.Taxi;

/**
 * Interface representing a booking state.
 *
 * @author vinodkandula
 */
public interface BookingState extends Serializable {

    /**
     * Cancel booking.
     *
     * @param booking booking callback.
     */
    public void cancelBooking(Booking booking);

    /**
     * Cancel taxi for booking.
     *
     * @param booking booking callback.
     */
    public void cancelTaxi(Booking booking);

    /**
     * Dispatch taxi.
     *
     * @param booking booking callback.
     * @param taxi taxi taking booking.
     */
    public void dispatchTaxi(Booking booking, Taxi taxi);

    /**
     * Drop off passenger.
     *
     * @param booking booking callback.
     * @param time time passenger arrived at their destination.
     * @throws IllegalArgumentException Invalid time. Start time cannot be
     * before booking creation timestamp.
     */
    public void dropOffPassenger(Booking booking, Date time);

    /**
     * Pickup passenger.
     *
     * @param booking booking callback
     * @param time time passenger picked up.
     * @throws IllegalArgumentException Invalid time. Start time cannot be
     * before booking creation timestamp.
     */
    public void pickupPassenger(Booking booking, Date time);
}
