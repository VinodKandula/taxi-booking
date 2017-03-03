package com.taxibooking.location.rest;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taxibooking.booking.model.Location;
import com.taxibooking.booking.model.taxi.Taxi;
import com.taxibooking.booking.repository.LocationRepository;
import com.taxibooking.booking.repository.TaxiRepository;
import com.taxibooking.booking.util.DataMapper;
import com.taxibooking.location.track.LocationTrackingService;

/**
 * Controller class for taxis.
 *
 * @author vinodkandula
 */
@RestController()
@RequestMapping("/v1/taxi")
public class TaxiController {

    private static final Logger LOGGER = Logger.getLogger(TaxiController.class.getName());

    @Autowired
    private LocationTrackingService locationTrackingService;
    
    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private TaxiRepository taxiRepository;
    
    private final DataMapper mapper;

    /**
     * Default constructor for class taxi controller.
     */
    public TaxiController() {
        this.mapper = DataMapper.getInstance();
    }

    /**
     * Update location of taxi associated with the provided id.
     *
     * @param id id of the taxi.
     * @param message the json update message.
     * @return update confirmation.
     */
    @RequestMapping(method=RequestMethod.POST, value="/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Location updateTaxiLocation(@PathVariable("id") Long id, @RequestBody String message) {
        try {

            Location location = this.mapper.readValue(message, Location.class);

            // drivers should only be able to update there own taxi.
            locationRepository.save(location);
            
            // event time is based on current server time.
            this.locationTrackingService.updateLocation(
                    id, location.getLatitude(), location.getLongitude(), new Date().getTime());

            
            return location;
            
         } catch (Exception ex) {
            LOGGER.log(Level.INFO, null, ex);
         } 
        return null;
    }

    /**
     * Find taxi by id.
     *
     * @param id taxi id.
     * @return taxi with specified id else 404.
     */
    @RequestMapping(method=RequestMethod.GET, value="/{id}")
    public Taxi getTaxi(@PathVariable("id") Long id) {

        Taxi taxi = this.taxiRepository.findOne(id);

        return taxi;
    }
}
