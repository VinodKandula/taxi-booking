package com.taxibooking.booking.repository.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taxibooking.booking.model.Account;
import com.taxibooking.booking.model.Route;
import com.taxibooking.booking.model.booking.Booking;
import com.taxibooking.booking.model.taxi.Taxi;
import com.taxibooking.booking.repository.AccountRepository;
import com.taxibooking.booking.repository.BookingRepository;
import com.taxibooking.booking.repository.RouteRepository;
import com.taxibooking.booking.repository.TaxiRepository;
import com.taxibooking.booking.rest.BookingDto;
import com.taxibooking.location.service.google.GoogleDistanceMatrixService;
import com.taxibooking.location.service.google.InvalidGoogleApiResponseException;

/**
 * A service class implementing the booking facade.
 *
 * @author vinodkandula
 */
@Component
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RouteRepository routeRepository;

	@Autowired
	private TaxiRepository taxiRepository;

	@Autowired
	private GoogleDistanceMatrixService googleDistanceMatrixService;

	public BookingServiceImpl() {

	}

	@Override
	public Booking findBooking(Long id) {
		return this.bookingRepository.findOne(id);
	}

	/**
	 * Return booking if booking id matches the authenticated user, else null.
	 *
	 * @param id
	 *            id of booking.
	 * @param username
	 *            username of authenticated user.
	 * @return return booking if booking id matches the authenticated user, else
	 *         null.
	 */
	@Override
	public Booking findBookingForUser(Long id, String username) {
		Booking booking = this.findBooking(id);

		if (booking != null && booking.getPassenger().getUsername().equals(username)) {
			return booking;
		} else {
			return null;
		}
	}

	/**
	 * Book a taxi.
	 *
	 * @param bookingDto
	 *            booking data transfer object. Requires: - username username of
	 *            passenger. - numberPassengers number of passengers. - pickup
	 *            location - destination location
	 * @throws AccountAuthenticationFailed
	 *             if account authentication fails.
	 * @throws RouteNotFoundException
	 *             route not found.
	 * @throws InvalidGoogleApiResponseException
	 *             invalid response from Google (Invalid JSON as there API has
	 *             changed).
	 * @throws InvalidBookingException
	 *             invalid booking e.g. user has active bookings.
	 */
	@Override
	public Booking makeBooking(final BookingDto bookingDto) {

		// TODO: validate BookingDto
		try {
			Account passenger = this.accountRepository.findOne(bookingDto.getPassengerUsername());

			if (passenger == null) {
				throw new RuntimeException("Account Passenger can't be null");
			}

			if (this.incompleteBookings(passenger.getUsername())) {

				throw new RuntimeException("Incomplete Booking");
			}

			Route route = this.googleDistanceMatrixService.getRouteInfo(bookingDto.getStartLocation(),
					bookingDto.getEndLocation());

			if (route == null) {
				throw new RuntimeException("Route is null");
			}

			Booking booking = new Booking(passenger, route, bookingDto.getNumberPassengers());

			this.bookingRepository.save(booking);

			return booking;
		} catch (Exception e) {
			throw new RuntimeException("Booking Failed!");
		}

	}

	/**
	 * Return true if incomplete bookings for user else false.
	 *
	 * @param username
	 *            username to check.
	 * @return true if incomplete bookings for user else false
	 */
	private boolean incompleteBookings(String username) {
		//TODO
		return false;
	}

	/**
	 * Update booking.
	 *
	 * @param booking
	 *            booking to update.
	 */
	@Override
	public void updateBooking(Booking booking) {
		this.bookingRepository.save(booking);
	}

	/**
	 * Find all bookings in awaiting taxi to be dispatched state.
	 *
	 * @return all bookings in awaiting taxi to be dispatched state.
	 */
	@Override
	public List<Booking> findBookingsInAwaitingTaxiDispatchState() {
		return this.bookingRepository.findByState(Booking.getAwaitingTaxiBookingState());
	}

	/**
	 * A collections of bookings. If passenger display booking history. If
	 * driver display job history. If passenger has multiple roles combine
	 * collections.
	 *
	 * @param username
	 *            username.
	 * @return return booking history.
	 */
	@Override
	public List<Booking> findBookingHistory(String username) {

		List<Booking> bookingHistory = new ArrayList<>();
		
		//TODO

		return bookingHistory;
	}

	/**
	 * Accept a taxi booking.
	 *
	 * @param username
	 *            username of taxi driver.
	 * @param bookingId
	 *            booking id.
	 * @throws TaxiNotFoundException
	 *             taxi not found.
	 * @throws BookingNotFoundException
	 *             booking not found.
	 * @throws IllegalBookingStateException
	 *             if booking in an illegal state.
	 */
	@Override
	public synchronized void acceptBooking(String username, long bookingId) {

		Taxi taxi = this.taxiRepository.findByAccount(this.accountRepository.findOne(username));
		Booking booking = this.findBooking(bookingId);

		if (taxi == null)
			throw new RuntimeException("Taxi is null!");
		if (booking == null)
			throw new RuntimeException("Booking is null!");

		try {
			booking.dispatchTaxi(taxi);

			taxi.acceptJob();
			this.taxiRepository.save(taxi);

			// send GCM/APNS notification.
			this.bookingRepository.save(booking);
		} catch (IllegalStateException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * Pink up passenger.
	 *
	 * @param username
	 *            username or driver accepting the booking
	 * @param bookingId
	 *            id of booking to update.
	 * @param timestamp
	 *            timestamp of update.
	 * @throws BookingNotFoundException
	 *             booking not found.
	 * @throws TaxiNotFoundException
	 *             taxi for user not found.
	 * @throws IllegalBookingStateException
	 *             if booking in an illegal state.
	 */
	@Override
	public void pickUpPassenger(String username, long bookingId, long timestamp){

		//TODO
	}

	/**
	 * Drop of passenger.
	 *
	 * @param bookingId
	 *            id of booking to update.
	 * @param timestamp
	 *            timestamp of update.
	 * @throws BookingNotFoundException
	 *             booking not found.
	 * @throws TaxiNotFoundException
	 *             taxi for user not found.
	 * @throws IllegalBookingStateException
	 *             if booking in an illegal state.
	 */
	@Override
	public void dropOffPassenger(String username, long bookingId, long timestamp) {

		//TODO
	}

	/**
	 * Cancel a booking.
	 *
	 * @param username
	 *            username of person requesting cancellation of booking.
	 * @param bookingId
	 *            booking to cancel.
	 * @throws BookingNotFoundException
	 *             booking not found.
	 * @throws AccountAuthenticationFailed
	 *             user does not have permission to cancel booking.
	 * @throws IllegalBookingStateException
	 *             if booking in an illegal state.
	 */
	@Override
	public void cancelBooking(String username, long bookingId){

		//TODO
	}

	/**
	 * Return most recent active booking for a user.
	 * 
	 * @param username
	 *            username of user.
	 * @return active booking for a user.
	 * @throws IllegalArgumentException
	 *             if username is null;
	 */
	@Override
	public Booking checkActiveBooking(String username) {

		if (username == null) {
			throw new IllegalArgumentException("Username cannot be null.");
		}

		List<Booking> bookings = this.bookingRepository.findByPassenger(this.accountRepository.findOne(username));

		if (bookings != null && bookings.size() > 0) {
			// return most recent booking
			Booking recentBooking = bookings.get(0);

			for (Booking b : bookings) {
				if (b.isActive() && !recentBooking.getTimestamp().after(b.getTimestamp())) {
					recentBooking = b;
				}
			}

			return !recentBooking.isActive() ? null : recentBooking;
		}

		return null;
	}

	/**
	 * Allocate taxi to current waiting bookings.
	 */
	@Override
	public void allocateTaxi() {

		List<Booking> bookings = this.findBookingsInAwaitingTaxiDispatchState();

		if (!bookings.isEmpty()) {
			Random random = new Random();
			int randomBooking = random.nextInt(bookings.size());

			this.allocateTaxi(bookings.get(randomBooking));
		}
	}

	@Override
	public void allocateTaxi(Booking booking) {
		//TODO

	}
}
