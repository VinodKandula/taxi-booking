package com.taxibooking.booking.model.taxi;

/**
 *
 * @author vinodkandula
 */
public class OffDutyTaxiState implements TaxiState {

    @Override
    public void goOffDuty(Taxi taxi) {
        throw new IllegalStateException("Already off duty.");
    }

    @Override
    public void goOnDuty(Taxi taxi) {
        taxi.setState(Taxi.getOnDutyTaxiState());
    }

    @Override
    public void acceptJob(Taxi taxi) {
        throw new IllegalStateException("Off duty.");
    }
    
    @Override
    public void completeJob(Taxi taxi) {
        throw new IllegalStateException("Taxi off duty.");
    }
    
    @Override
    public String toString(){
        return "Off duty.";
    }

}
