package com.taxibooking.location.track;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.taxibooking.booking.model.Location;
import com.taxibooking.booking.model.mapping.JpaTaxiStateDataConverter;
import com.taxibooking.booking.model.taxi.Taxi;
import com.taxibooking.booking.repository.LocationRepository;
import com.taxibooking.booking.repository.TaxiRepository;

/**
 * Location tracking service facade implementation.
 *
 * @author vinodkandula
 */
@Component
@Transactional
public class LocationTrackingServiceImpl extends Subject implements LocationTrackingService {

    private TaxiRepository taxiRepo;
    
    private LocationRepository locationRepo;

    /**
     * Update taxi location by id. Find taxi by id and set new location and
     * broadcast update taxi location.
     *
     * @param id the id of the taxi.
     * @param latitude Taxi's current latitude.
     * @param longitude Taxi's current longitude.
     * @param timestamp timestamp of event.
     * @throws TaxiNotFoundException taxi not found.
     * @IllegalArgumentException invalid latitude or longitude.
     */
    @Override
    public synchronized void updateLocation(Long id, double latitude, double longitude, long timestamp) throws Exception {
        Location lastKnownLocation;

        try {
            lastKnownLocation = new Location(latitude, longitude);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }

        Taxi taxi = this.taxiRepo.findOne(id);

        if (taxi == null) {
            throw new Exception("Taxi Not Found");
        }

        Location previousLocation = taxi.getLocation();
        taxi.updateLocation(lastKnownLocation);

        if (previousLocation == null) {
            this.locationRepo.save(lastKnownLocation);
        }

        this.taxiRepo.save(taxi);

        // create taxi location event suitable for data transfer.
        TaxiLocationEventDto event = new TaxiLocationEventDto(id,
                new JpaTaxiStateDataConverter().convertToDatabaseColumn(taxi.getState()),
                lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(),
                timestamp);

        //notify all taxi subscribers of updated taxi location
        this.notifyObservers(event);
    }

    /**
     * Notify observer if within grid reference or all.
     *
     * @param event
     */
    @Override
    public void notifyObservers(TaxiLocationEventDto event) {

        for (LocationTrackingObserver o : this.getObservers()) {
            o.update(event);
        }
    }
}
