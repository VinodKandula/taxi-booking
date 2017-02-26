package com.taxibooking.location.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taxibooking.booking.model.Location;
import com.taxibooking.booking.model.Route;
import com.taxibooking.location.service.google.GoogleDistanceMatrixService;
import com.taxibooking.location.service.google.InvalidGoogleApiResponseException;

/**
 * A controller class for receiving and handling all geocoding related transactions.
 *
 * @author vinodkandula
 */


@RestController()
@RequestMapping("/v1/geocode")
public class GeocodeController {

    private static final Logger LOGGER = Logger.getLogger(GeocodeController.class.getName());

    @Autowired
    private GoogleDistanceMatrixService googleDistanceMatrixService;

    public GeocodeController() {
    }

    /**
     * Return a route from start and end location latitude and longitude.
     *
     * @param url url with start_latitude, start_longitude, end_latitude and end_longitude query parameters.
     * @return a route from start to end location using provided a latitudes and longitudes.
     */
    
    @RequestMapping(method=RequestMethod.GET, value="/route")
    public Route getRouteInfo(@RequestParam MultiValueMap<String, String> parameters) throws Exception {
        try {
            if (!(parameters.containsKey("start_latitude") && parameters.containsKey("end_latitude")
                    && parameters.containsKey("end_longitude") && parameters.containsKey("start_longitude"))) {
                throw new IllegalArgumentException("Latitude and longitude parameter must be provided for start and end locations.");
            }

            double start_latitude = Double.parseDouble(parameters.getFirst("start_latitude"));
            double start_longitude = Double.parseDouble(parameters.getFirst("start_longitude"));
            double end_latitude = Double.parseDouble(parameters.getFirst("end_latitude"));
            double end_longitude = Double.parseDouble(parameters.getFirst("end_longitude"));

            Route route = this.googleDistanceMatrixService.getRouteInfo(new Location(start_latitude, start_longitude), new Location(end_latitude, end_longitude));

            return route;

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.INFO, null, ex);
            throw ex;
        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
            throw ex;
        }
    }

    /**
     * Return address corresponding to provided latitude and longitude.
     *
     * @param url url with latitude and longitude query parameters.
     * @return address corresponding to provided latitude and longitude.
     */
    @RequestMapping(method=RequestMethod.GET, value="/reverse")
    //@RolesAllowed({"driver", "passenger"})
    public String addressReverseLookup(@RequestParam MultiValueMap<String, String> parameters) {
        try {

            if (!(parameters.containsKey("latitude") && parameters.containsKey("longitude"))) {
                throw new IllegalArgumentException("Latitude and longitude parameter must be provided.");
            }

            double latitude = Double.parseDouble(parameters.getFirst("latitude"));
            double longitude = Double.parseDouble(parameters.getFirst("longitude"));

            if (!Location.validateCoordinates(latitude, longitude)) {
                throw new IllegalArgumentException("Latitude or longitude are invalid.");
            }

            LOGGER.log(Level.INFO, "addressReverseLookup - LatLng {0},{1}",
                    new Object[]{latitude, longitude});

            String address = this.googleDistanceMatrixService.getGeocode(
                    latitude, longitude);

            LOGGER.log(Level.INFO, "addressReverseLookup - {0}", address);

            // Zero resuls found for address reverse lookup using latitude and longitude
            if (address == null) {
                return "Address not found.";
            }

            return address;

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.INFO, null, ex);

        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
        }
        return "Error";
    }

    /**
     * Return location (lat/lng) corresponding to provided address.
     *
     * @param url url with address query parameter.
     * @return address corresponding to provided latitude and longitude.
     */
    @RequestMapping(method=RequestMethod.GET, value="/address/lookup")
    public Location addressLookup(@RequestParam MultiValueMap<String, String> parameters) {
        try {

            if (!(parameters.containsKey("address"))) {
                throw new IllegalArgumentException("Address must be provided");
            }

            String address = parameters.getFirst("address");

            LOGGER.log(Level.INFO, "addressLookup - {0}", address);

            Location location = this.googleDistanceMatrixService.getGeocode(
                    address);

            if (location == null) {
                return null;
            }

            LOGGER.log(Level.INFO, "addressLookup - LatLng {0},{1}",
                    new Object[]{location.getLatitude(), location.getLongitude()});

            return location;

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.INFO, null, ex);

        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
        }
        return null;
    }

    /**
     * Return estimate travel time between start and end location in seconds.
     *
     * @param url url with start_latitude, start_longitude, end_latitude and end_longitude query parameters.
     * @return a route from start to end location using provided a latitudes and longitudes.
     */
    @RequestMapping(method=RequestMethod.GET, value="/route/estimate/time")
    public double estimateTravelTime(@RequestParam MultiValueMap<String, String> parameters) {
        try {
            if (!(parameters.containsKey("start_latitude") && parameters.containsKey("end_latitude")
                    && parameters.containsKey("end_longitude") && parameters.containsKey("start_longitude"))) {
                throw new IllegalArgumentException("Latitude and longitude parameter must be provided for start and end locations.");
            }

            double start_latitude = Double.parseDouble(parameters.getFirst("start_latitude"));
            double start_longitude = Double.parseDouble(parameters.getFirst("start_longitude"));
            double end_latitude = Double.parseDouble(parameters.getFirst("end_latitude"));
            double end_longitude = Double.parseDouble(parameters.getFirst("end_longitude"));

            double time = this.googleDistanceMatrixService.estimateTravelTime(new Location(start_latitude, start_longitude), new Location(end_latitude, end_longitude));

            return time;

        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
        }
        return 0;
    }

    /**
     * Find address via textual description.
     *
     * @param url url with address query parameter.
     * @return the found address, else "Address not found.".
     */
    @RequestMapping(method=RequestMethod.GET, value="/address")
    public String findAddress(@RequestParam MultiValueMap<String, String> parameters) {
        try {
            if (!(parameters.containsKey("address"))) {
                throw new IllegalArgumentException("Address must be provided.");
            }

            String address = this.googleDistanceMatrixService.findAddress(parameters.getFirst("address"));

            if (address == null) {
                return "Address not found.";
            }

            return address;

        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
        }
        return null;
    }
}
