package com.taxibooking.booking.model.taxi;

import java.io.Serializable;

/**
 * Interface representing a state a taxi can be in and common operations.
 * State design pattern. A behavioral pattern that provide a flexible
 * alternative to subclasses and complex difficult to test conditional
 * statements.
 *
 * @author vinodkandula
 */
public interface TaxiState extends Serializable {

    public void goOffDuty(Taxi taxi);

    public void goOnDuty(Taxi taxi);

    public void acceptJob(Taxi taxi);
    
    public void completeJob(Taxi taxi);
}
