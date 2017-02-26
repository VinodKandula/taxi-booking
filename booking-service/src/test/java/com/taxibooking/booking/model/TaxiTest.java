package com.taxibooking.booking.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.taxibooking.booking.model.taxi.Taxi;

/**
 * Unit tests for taxi.
 *
 * @author vinodkandula
 */
public class TaxiTest {

    private VehicleType type;
    private Vehicle vehicle;
    private Taxi taxi;
    private Account driver;

    @Before
    public void setUp() {
        driver = new Account("vinodkandula","Vinod", "Kandula", "simple_password", "vinod.kandula@gmail.com", "07888888826");
        driver.setRole(AccountRole.DRIVER);

        type = new VehicleType("Taxi", "Ford", "Focus", 0.3);
        vehicle = new Vehicle("AS10 AJ", 5, type);
        taxi = new Taxi(vehicle, driver);
    }

    /**
     * Test taxi check seat availability.
     */
    @Test
    public void testCheckSeatAvailability1() {
        this.vehicle.setNumberSeats(5);
        assertTrue(this.taxi.checkseatAvailability(4));
    }
    
    /**
     * Test taxi check seat availability.
     */
    @Test
    public void testCheckSeatAvailability2() {
        this.vehicle.setNumberSeats(5);
        assertFalse(this.taxi.checkseatAvailability(5));
    }
}
