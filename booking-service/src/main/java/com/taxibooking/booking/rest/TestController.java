package com.taxibooking.booking.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taxibooking.booking.model.Account;
import com.taxibooking.booking.model.AccountRole;
import com.taxibooking.booking.model.Location;
import com.taxibooking.booking.model.Route;
import com.taxibooking.booking.model.Vehicle;
import com.taxibooking.booking.model.VehicleType;
import com.taxibooking.booking.model.booking.Booking;
import com.taxibooking.booking.model.booking.CompletedBookingState;
import com.taxibooking.booking.model.taxi.Taxi;
import com.taxibooking.booking.repository.AccountRepository;
import com.taxibooking.booking.repository.BookingRepository;
import com.taxibooking.booking.repository.TaxiRepository;
import com.taxibooking.booking.repository.VehicleRepository;
import com.taxibooking.booking.security.AuthenticationUtils;
import com.taxibooking.location.service.google.GoogleDistanceMatrixService;
import com.taxibooking.location.service.google.InvalidGoogleApiResponseException;


/**
 * Test controller to initialise the database. 
 * 
 * @author vinodkandula
 */
@RestController()
@RequestMapping("/v1/test")
public class TestController {
	
	@Autowired
	private AccountRepository accountRepository; 
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private TaxiRepository taxiRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private GoogleDistanceMatrixService googleMapAPIService;

	@RequestMapping(method=RequestMethod.GET, value="/setup")
    public String setUp() throws InvalidGoogleApiResponseException {

        String password = AuthenticationUtils.hashPassword("password");

        Account passenger = new Account("vinod.kandula", "Vinod", "Kandula", AuthenticationUtils.hashPassword("vinod"), "07645346541", "vinod.kandula@gmail.com");
        passenger.setRole(AccountRole.PASSENGER);
        
        Account passenger2 = new Account("john.mac", "John", "Mac", password, "07645346541", "john.mac@example.com");
        passenger2.setRole(AccountRole.PASSENGER);

        Account passenger3 = new Account("tim.smith", "Tim", "Smith", password, "07526888826", "tim_smith@example.com");
        passenger3.setRole(AccountRole.PASSENGER);
       
        Account passenger4 = new Account("yorkie", "Poly", "Morphism", password, "02555346548", "yorkie@example.com");
        passenger4.setRole(AccountRole.PASSENGER);
        passenger4.setInActive();
        
        Account driver = new Account("john.smith", "John", "Smith", password, "02345346548", "john.smith@example.com");
        driver.setRole(AccountRole.DRIVER);
        
        Account driver2 = new Account("joe.bloggs", "Joe", "Bloggs", password, "02345346548", "jblogs99@example.com");
        driver2.setRole(AccountRole.DRIVER);

        VehicleType type = new VehicleType("Cruiser", "Hyundai", "Matrix", 0.3);
        VehicleType type2 = new VehicleType("People Carrier", "Honda", "Civic", 0.5);
        
        Vehicle vehicle = new Vehicle("RN12 NGE", 5, type);
        Vehicle vehicle2 = new Vehicle("RN13 NGB", 5, type2);
        
        Taxi taxi = new Taxi(vehicle, driver);
        Taxi taxi2 = new Taxi(vehicle2, driver2);

        Location startLocation = new Location(51.763366, -0.22309);
        Location endLocation = new Location(51.7535889, -0.2446257);

        Route route = googleMapAPIService.getRouteInfo(startLocation, endLocation);

        Booking booking = new Booking(passenger, route, 2);
        booking.setState(new CompletedBookingState());

        /*
         * Persist entities.
         */
        
        this.accountRepository.save(passenger);
        this.accountRepository.save(passenger2);
        this.accountRepository.save(passenger3);
        this.accountRepository.save(passenger4);
        this.accountRepository.save(driver);
        this.accountRepository.save(driver2);
        
        this.vehicleRepository.save(vehicle);
        this.vehicleRepository.save(vehicle2);
        
        this.taxiRepository.save(taxi);
        this.taxiRepository.save(taxi2);
        
        this.bookingRepository.save(booking);
        
        return "Database initialised.";
    }

}
