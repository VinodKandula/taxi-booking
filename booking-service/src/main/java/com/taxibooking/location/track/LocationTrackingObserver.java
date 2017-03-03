package com.taxibooking.location.track;

/**
 * Observer interface implementation for the Observer pattern.
 *
 * @author vinodkandula
 */
public interface LocationTrackingObserver {

    /**
     * Method called whenever subject state changes.
     *
     * @param obj updated event.
     */
    public void update(Object obj);
}
