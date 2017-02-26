package com.taxibooking.booking.model.booking;

import java.util.Date;

import com.taxibooking.booking.model.taxi.Taxi;

/**
 * Represents completed booking state.
 *
 * @author vinodkandula
 */
public class CompletedBookingState implements BookingState {

    private IllegalStateException e = new IllegalStateException("Booking completed.");

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
        return "Completed booking state.";
    }
}
