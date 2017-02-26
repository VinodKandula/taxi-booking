package com.taxibooking.booking.model.taxi;

/**
 *
 * @author vinodkandula
 */
public class AcceptedJobTaxiState implements TaxiState {

    @Override
    public void goOffDuty(Taxi taxi) {
        throw new IllegalStateException("Cannot off duty after accepting a booking.");
    }

    @Override
    public void acceptJob(Taxi taxi) {
        throw new IllegalStateException("Already accepting booking");
    }

    @Override
    public void goOnDuty(Taxi taxi) {
        taxi.setState(Taxi.getOnDutyTaxiState());
    }
    
    @Override
    public void completeJob(Taxi taxi) {
        taxi.setState(Taxi.getAcceptedJobTaxiState());
    } 
    
    @Override
    public String toString(){
        return "On job.";
    }
}
