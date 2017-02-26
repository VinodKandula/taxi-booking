package com.taxibooking.booking.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Account class.
 * @author vinodkandula
 */
public class AccountTest {

    
    private Account account;
    
    @Before
    public void setUp(){
        account = new Account("vinodkandula","Vinod", "Kandula", "simple_password", "vinod.kandula@gmail.com", "07888888826");
        account.setRole(AccountRole.PASSENGER);
    }
    /**
     * Test of checkPassword method, of class Account.
     */
    @Test
    public void testCheckPassword() {
        assertTrue(account.checkPassword("simple_password"));
    }

    /**
     * Test of hasRole method, of class Account.
     * Intent: to test if enum comparison works as expected, due to previous defects.
     */
    @Test
    public void testHasRole() {
        assertFalse(account.hasRole("ADMIN"));
        assertTrue(account.hasRole("PASSENGER"));
        assertTrue(account.hasRole("passenger"));
        assertFalse(account.hasRole("unknown"));
    }
}
