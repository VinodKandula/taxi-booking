package com.taxibooking.location.track;

/**
 * An interface for defining and enforcing operations needed for the 
 * location tracking of taxis.
 *
 * @author vinodkandula
 */
public interface LocationTrackingService {

    /**
     * Update taxi location by id.
     *
     * @param id the id of the taxi.
     * @param latitude Taxi's current latitude.
     * @param longitude Taxi's current longitude.
     * @param timestamp timestamp of event.
     * @throws TaxiNotFoundException taxi not found.
     */
    public void updateLocation(Long id, double latitude, double longitude, long timestamp) throws Exception;
    
}
