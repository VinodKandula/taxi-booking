package com.taxibooking.location.track;

import java.util.HashSet;
import java.util.Set;

/**
 * Subject class implementation for the observer pattern.
 *
 * @author vinodkandula
 */
public abstract class Subject {

    private Set<LocationTrackingObserver> observers;

    public Subject() {
        this.observers = new HashSet<>();
    }

    /**
     * Register observer to subject.
     *
     * @param observer observer to register.
     */
    public void registerObserver(LocationTrackingObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Unsubscribe observer from the subject.
     *
     * @param observer observer to unsubscribe.
     */
    public void removeObserver(LocationTrackingObserver observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers of state change.
     *
     * @param obj updated object.
     */
    public void notifyObservers(TaxiLocationEventDto obj) {
        for (LocationTrackingObserver o : this.observers) {
            o.update(obj);
        }
    }
    
    /**
     * Return observers.
     * @return a set of location tracking WebSocketObservers
     */
    public Set<LocationTrackingObserver> getObservers(){
        return this.observers;
    }
}
