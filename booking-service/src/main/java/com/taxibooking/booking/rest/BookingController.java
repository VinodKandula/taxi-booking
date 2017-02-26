package com.taxibooking.booking.rest;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taxibooking.booking.model.booking.Booking;
import com.taxibooking.booking.repository.service.BookingService;
import com.taxibooking.booking.util.DataMapper;

/**
 * A controller class for receiving and handling all booking related
 * transactions.
 *
 * @author vinodkandula
 */
@RestController()
@RequestMapping("/v1/booking")
public class BookingController {

	private static final Logger LOGGER = Logger.getLogger(BookingController.class.getName());

	private final DataMapper mapper;

	@Autowired
	private BookingService bookingService;

	public BookingController() {
		this.mapper = DataMapper.getInstance();
	}

	/**
	 * Create a new taxi booking.
	 *
	 * @param securityContext
	 *            injected by request scope
	 * @param message
	 *            JSON representation of a booking data access object.
	 * @return booking object with estimated cost, distance, and travel time. A
	 *         driver will not have been notified at this time.
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Booking makeBooking(@RequestBody String message) throws Exception {

		BookingDto bookingDto = null;
		Booking booking = null;

		bookingDto = this.mapper.readValue(message, BookingDto.class);

		/*
		 * Security flow - don't allow people to book taxis for other user's.
		 * Set username from security context.
		 */

		booking = this.bookingService.makeBooking(bookingDto);
		return booking;
	}

	/**
	 * Return booking history based on user roles.
	 *
	 * @param securityContext
	 * @return booking history based on user roles.
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Booking> bookingHistory(String user) {

		return bookingService.findBookingHistory(user);

	}


	
}
