package com.taxibooking.booking.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxibooking.booking.model.Account;
import com.taxibooking.booking.model.booking.Booking;
import com.taxibooking.booking.model.booking.BookingState;

/**
 * A BookingRepository class for handling and managing event
 * related data requested, updated, and processed in the application and
 * maintained in the database.
 *
 * @author vinodkandula
 */
@Transactional
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Return a collection of bookings in the specified state.
     *
     * @param state state of taxi.
     * @return a list of all bookings in the specified state. A collection with
     * 0 elements is returned if there is no bookings making the provided state
     */
    public List<Booking> findByState(BookingState state);

    /**
     * Return a collections of bookings corresponding to the provided user.
     *
     * @param username username of passenger.
     * @return a collections of bookings corresponding to the provided user.
     */
    public List<Booking> findByPassenger(Account passenger);
       
    /**
     * Return a collections of bookings corresponding to the provided driver.
     *
     * @param username username of driver.
     * @return a collections of bookings corresponding to the provided driver.
     */
    
 
}
