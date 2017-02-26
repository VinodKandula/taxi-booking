package com.taxibooking.booking.model.taxi;

/**
 * Represents taxi states.
 * @author vinodkandula
 */
public enum TaxiStates {
    OFF_DUTY("OFF_DUTY"),
    ON_DUTY("ON_DUTY"),
    ON_JOB("ON_JOB");
    
    private String textualDescription;
    
    /**
     * Default constructor.
     * 
     * @param description string representation of a taxi state.
     */
    TaxiStates(String description){
        this.textualDescription = description;
    }
    
    @Override
    public String toString(){
        return this.textualDescription;
    }
}
