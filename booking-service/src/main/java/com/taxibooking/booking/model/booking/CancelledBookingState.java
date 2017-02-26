package com.taxibooking.booking.model.booking;

import java.util.Date;

import com.taxibooking.booking.model.taxi.Taxi;

/**
 * Represents the canceled taxi booking state.
 *
 * @author vinodkandula
 */
public class CancelledBookingState implements BookingState {

    private IllegalStateException e = new IllegalStateException("Booking already canceled.");

    @Override
    public void cancelBooking(Booking booking) {
        throw e;
    }

    @Override
    public void cancelTaxi(Booking booking) {
        throw e;
    }

    @Override
    public void dispatchTaxi(Booking booking, Taxi taxi) {
        throw e;
    }

    @Override
    public void dropOffPassenger(Booking booking, Date time) {
        throw e;
    }

    @Override
    public void pickupPassenger(Booking booking, Date time) {
        throw e;
    }

    @Override
    public String toString() {
        return "Booking canceled state.";
    }
}
