package com.taxibooking.location.service.google;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import com.taxibooking.booking.model.Location;
import com.taxibooking.booking.model.Route;

/**
 * An interface for defining and enforcing operations needed for interfacing
 * with Google distance matrix and geocode services.
 *
 * @author vinodkandula
 */
public interface GoogleDistanceMatrixService {

    /**
     * Return distance between origin and destination.
     *
     * @param origin route origin address.
     * @param destination route destination address.
     * @return distance between origin and destination.
     * @throws InvalidGoogleApiResponseException
     */
    public double getDistance(String origin, String destination) throws InvalidGoogleApiResponseException;
 
    /**
     * Return a collection of routes from Google directions API response.
     *
     * @param json Google directions API response.
     * @return a collection of routes from Google directions API response.
     * @throws InvalidGoogleApiResponseException invalid JSON response.
     */
    public List<List<Location>> getRoutes(JSONObject json) throws InvalidGoogleApiResponseException;

    /**
     * Return location from address.
     *
     * @param address address to lookup.
     * @return the location (latitude and longitude)
     * @throws InvalidGoogleApiResponseException invalid JSON response.
     */
    public Location getGeocode(String address) throws InvalidGoogleApiResponseException;

    /**
     * Return address corresponding to latitude and longitude.
     *
     * @param latitude latitude of address.
     * @param longitude longitude of address.
     * @return address corresponding to latitude and longitude.
     * @throws InvalidGoogleApiResponseException invalid JSON response.
     */
    public String getGeocode(double latitude, double longitude) throws InvalidGoogleApiResponseException;

    /**
     * Get route info (distance, route, travel time, start and end textual
     * address) using start and end location.
     *
     * @param startLocation start location
     * @param endLocation end location.
     * @return route calculated route information.
     * @throws InvalidGoogleApiResponseException unable to parse API response.
     * @throws IllegalArgumentException invalid location object.
     */
    public Route getRouteInfo(Location startLocation, Location endLocation) throws InvalidGoogleApiResponseException;

    /**
     * Estimate travel time between start and end location.
     *
     * @param startLocation start location
     * @param endLocation end location.
     * @return route calculated route information.
     * @throws InvalidGoogleApiResponseException unable to parse API response.
     * @throws IllegalArgumentException invalid location object.
     */
    public long estimateTravelTime(Location startLocation, Location endLocation) throws InvalidGoogleApiResponseException;
    
    /**
     * Find address via textual description.
     * @param address address to search.
     * @return complete address.
     * @throws InvalidGoogleApiResponseException 
     */
    public String findAddress(String address) throws InvalidGoogleApiResponseException;
}
