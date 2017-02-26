package com.taxibooking.location.service.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.taxibooking.booking.model.Address;
import com.taxibooking.booking.model.Location;
import com.taxibooking.booking.model.Route;
import com.taxibooking.booking.util.ConfigService;
import com.taxibooking.booking.util.HttpUtils;

/**
 * Google distance matrix service implementation.
 *
 * @author vinodkandula
 */
@Component
public class GoogleDistanceMatrixServiceImpl implements GoogleDistanceMatrixService {

    private static final Logger LOGGER = Logger.getLogger(GoogleDistanceMatrixServiceImpl.class.getName());

    @Autowired
    @Value("${google.distancematrix.api.distance.lookup}")
    private String distanceLookupQuery;
    
    @Autowired
    @Value("${google.geocoding.api.address.lookup}")
    private String addressLookupQuery;
    
    @Autowired
    @Value("${google.geocoding.api.address.latlng.lookup}")
    private String addressLatLngLookupQuery;
    
    @Autowired
    @Value("${google.geocoding.api.address.lookup.text}")
    private String addressLookupTextQuery;
    
    @Autowired
    @Value("${google.directions.api.route.lookup}")
    private String routeLookupQuery;
    
    @Autowired
    @Value("${google.maps.api.endpoint}")
    private String googleMapEndPoint;
    
    @Autowired
    @Value("${google.api.key}")
    private String googleAPIKey;
    
    @Autowired
    @Value("${google.units}")
    private String googleUnits;
    
    /**
     * Default constructor.
     */
    public GoogleDistanceMatrixServiceImpl() {
    }

    /**
     * Example: https://maps.googleapis.com/maps/api/distancematrix/json?origins=30%20Cheviots%20hatfield&destinations=welwyn
     *
     * @param origin origin to search from
     * @param destination destination
     * @throws InvalidGoogleApiResponseException unable to parse API response.
     * @throws RouteNotFoundException route not found.
     */
    @Override
    public double getDistance(String origin, String destination) throws InvalidGoogleApiResponseException{

        try {
            Map<String, String> tokens = new HashMap<>();
            tokens.put("origins", HttpUtils.stringEncode(origin));
            tokens.put("destinations", HttpUtils.stringEncode(destination));
            tokens.put("google.maps.api.endpoint", this.googleMapEndPoint);
            tokens.put("google.api.key", this.googleAPIKey);
            tokens.put("google.units", this.googleUnits);
            
            String query = ConfigService.parseProperty(this.distanceLookupQuery, tokens);

            LOGGER.log(Level.FINEST, query);

            JSONObject json = HttpUtils.getUrl(query);

            LOGGER.log(Level.FINEST, json.toString());

            LOGGER.log(Level.INFO, json.toString());
            if (!(json.toString().contains("ZERO_RESULTS") || json.toString().contains("NOT_FOUND"))) {
                return json.getJSONArray("rows")
                        .getJSONObject(0)
                        .getJSONArray("elements")
                        .getJSONObject(0)
                        .getJSONObject("distance")
                        .getDouble("value");
            } else {
                throw new RuntimeException();
            }

        } catch (JSONException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new InvalidGoogleApiResponseException();
        }

    }

    /**
     * Perform a Geocode reverse lookup using latitude and longitude. Query example:
     * http://maps.googleapis.com/maps/api/geocode/json?latlng=44.4647452,7.3553838
     *
     * @param latitude latitude.
     * @param longitude longitude.
     * @return Return address if found els, null.
     * @throws InvalidGoogleApiResponseException unable to parse API response.
     */
    @Override
    public String getGeocode(double latitude, double longitude) throws InvalidGoogleApiResponseException{
        Map<String, String> tokens = new HashMap<>();
        tokens.put("latitude", String.valueOf(latitude));
        tokens.put("longitude", String.valueOf(longitude));
        tokens.put("google.maps.api.endpoint", this.googleMapEndPoint);
        tokens.put("google.api.key", this.googleAPIKey);
        tokens.put("google.units", this.googleUnits);
        
        String query = ConfigService.parseProperty(this.addressLookupQuery, tokens);

        try {

            LOGGER.log(Level.INFO, "getGeocode query - {0}", query);

            JSONObject json = HttpUtils.getUrl(query);

            LOGGER.log(Level.INFO, "getGeocode - {0}", json);

            if (!"ZERO_RESULTS".equals(json.getString("status"))) {
                JSONArray results = json.getJSONArray("results");
                JSONObject object = results.getJSONObject(0);

                return object.getString("formatted_address");
            } else {
                return null;
            }
        } catch (JSONException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new InvalidGoogleApiResponseException();
        }
    }

    /**
     * Use Google Geocoding API to determine address coordinates.
     *
     * Example query: http://maps.googleapis.com/maps/api/geocode/json?address=%22po5%201pl%22
     *
     * @param address address to lookup.
     * @return location of specified address.
     * @throws InvalidGoogleApiResponseException unable to parse API response.
     */
    @Override
    public Location getGeocode(String address) throws InvalidGoogleApiResponseException {
        try {
             Map<String, String> tokens = new HashMap<>();
            tokens.put("address", HttpUtils.stringEncode(address));
            tokens.put("google.maps.api.endpoint", this.googleMapEndPoint);
            tokens.put("google.api.key", this.googleAPIKey);
            tokens.put("google.units", this.googleUnits);
            
            String query = ConfigService.parseProperty(this.addressLatLngLookupQuery, tokens);

            JSONObject json = HttpUtils.getUrl(query);

            if (this.validateJsonResponse(json)) {
                JSONArray results = json.getJSONArray("results");
                JSONObject object = results.getJSONObject(0);

                JSONObject location = object.getJSONObject("geometry").getJSONObject("location");

                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");

                return new Location(lat, lng);
            } else {
                return null;
            }
        } catch (JSONException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new InvalidGoogleApiResponseException();
        }
    }

    @Override
    public String findAddress(String address) throws InvalidGoogleApiResponseException{

        try {
            Map<String, String> tokens = new HashMap<>();
            tokens.put("address", HttpUtils.stringEncode(address));
            tokens.put("google.maps.api.endpoint", this.googleMapEndPoint);
            tokens.put("google.api.key", this.googleAPIKey);
            tokens.put("google.units", this.googleUnits);
            
            String query = ConfigService.parseProperty(this.addressLookupTextQuery, tokens);

            JSONObject json = HttpUtils.getUrl(query);

            if (this.validateJsonResponse(json)) {
                JSONArray results = json.getJSONArray("results");
                JSONObject object = results.getJSONObject(0);

                String location = object.getString("formatted_address");

                return location;
            } else {
                return null;
            }
        } catch (JSONException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new InvalidGoogleApiResponseException();
        }

    }

    /**
     * Get route info (distance, route, travel time, start and end textual address) using start and end location.
     *
     * @param startLocation start location
     * @param endLocation end location.
     * @return route
     * @throws InvalidGoogleApiResponseException unable to parse API response.
     */
    @Override
    public Route getRouteInfo(Location startLocation, Location endLocation)
            throws InvalidGoogleApiResponseException {

        List<List<Location>> routes;
        List<Location> path;
        Address startAddress;
        Address endAddress;
        double distance;
        double estimatedTravelTime;
        Route route = null;

        Map<String, String> tokens = new HashMap<>();
        tokens.put("origin", startLocation.toString());
        tokens.put("destination", endLocation.toString());
        tokens.put("google.maps.api.endpoint", this.googleMapEndPoint);
        tokens.put("google.api.key", this.googleAPIKey);
        tokens.put("google.units", this.googleUnits);
        
        String query = ConfigService.parseProperty(this.routeLookupQuery, tokens);

        LOGGER.log(Level.INFO, "getRouteInfo - {0}", query);

        try {

            LOGGER.log(Level.INFO, "getRouteInfo - {0}", query);

            JSONObject json = HttpUtils.getUrl(query);

            LOGGER.log(Level.INFO, "getRouteInfo - {0}", json);

            if (this.validateJsonResponse(json)) {

                routes = this.getRoutes(json);

                if (routes != null && !routes.isEmpty()) {
                    path = routes.get(0);
                } else {
                    // no route.
                    return null;
                }

                JSONArray legs = json.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
                startAddress = new Address(legs.getJSONObject(0).getString("start_address"), startLocation);
                endAddress = new Address(legs.getJSONObject(0).getString("end_address"), endLocation);

                // distance in km
                distance = legs.getJSONObject(0).getJSONObject("distance").getDouble("value");

                //time in seconds
                estimatedTravelTime = legs.getJSONObject(0).getJSONObject("duration").getDouble("value");

                route = new Route(startAddress, endAddress, distance, path, estimatedTravelTime);
            }
            return route;
        } catch (JSONException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new InvalidGoogleApiResponseException();
        }
    }

    /**
     * Return true if JSON valid else false.
     *
     * @param json to validate.
     * @return true if JSON valid else false.
     */
    private boolean validateJsonResponse(JSONObject json) {
        try {
            return !"ZERO_RESULTS".equals(json.getString("status"));
        } catch (JSONException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Get routes from valid Google Directions API response. Pre-condition: response contain a valid route e.g. API response status is OK. Example
     *
     * Example query: https://maps.googleapis.com/maps/api/directions/json?origin =place_id:ChIJ685WIFYViEgRHlHvBbiD5nE&destination=
     * place_id:ChIJA01I-8YVhkgRGJb0fW4UX7Y&key=AIzaSyDY6IVa8pcYJD4lMDbFaCayQr6327cyqMc
     *
     * @param json json to parse.
     * @return a collection of routes.
     * @throws InvalidGoogleApiResponseException unable to parse API response.
     */
    @Override
    public List<List<Location>> getRoutes(JSONObject json) throws InvalidGoogleApiResponseException {

        List<List<Location>> routesList = new ArrayList<>();
        JSONArray routes = null;
        JSONArray legs = null;
        JSONArray steps = null;

        try {

            routes = json.getJSONArray("routes");

            for (int i = 0; i < routes.length(); i++) {
                legs = ((JSONObject) routes.get(i)).getJSONArray("legs");
                List<Location> path = new ArrayList<>();

                for (int j = 0; j < legs.length(); j++) {
                    steps = ((JSONObject) legs.get(j)).getJSONArray("steps");

                    for (int k = 0; k < steps.length(); k++) {
                        String polyline = (String) ((JSONObject) ((JSONObject) steps.get(k)).get("polyline")).get("points");
                        List<Location> list = PolyLineUtils.decodePoly(polyline);

                        for (Location location : list) {
                            path.add(location);
                        }
                    }
                }
                routesList.add(path);
            }
        } catch (JSONException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InvalidGoogleApiResponseException();
        }
        return routesList;
    }

    /**
     * Estimate travel time between start and end location.
     *
     * @param startLocation start location
     * @param endLocation end location.
     * @return route calculated route information.
     * @throws InvalidGoogleApiResponseException unable to parse API response.
     * @throws IllegalArgumentException invalid location object.
     */
    @Override
    public long estimateTravelTime(Location startLocation, Location endLocation) throws InvalidGoogleApiResponseException {

        try {
            long estimatedTravelTime = 0;

            Map<String, String> tokens = new HashMap<>();
            tokens.put("origin", startLocation.toString());
            tokens.put("destination", endLocation.toString());
            tokens.put("google.maps.api.endpoint", this.googleMapEndPoint);
            tokens.put("google.api.key", this.googleAPIKey);
            tokens.put("google.units", this.googleUnits);
            
            String query = ConfigService.parseProperty(this.routeLookupQuery, tokens);

            LOGGER.log(Level.FINEST, "estimateTravelTime - {0}", query);

            JSONObject json = HttpUtils.getUrl(query);

            LOGGER.log(Level.FINEST, "estimateTravelTime - {0}", json);

            if (this.validateJsonResponse(json)) {
                JSONArray legs = json.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
                //time in seconds
                estimatedTravelTime = (long) legs.getJSONObject(0).getJSONObject("duration").getDouble("value");
            }
            return estimatedTravelTime;

        } catch (JSONException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new InvalidGoogleApiResponseException();
        }
    }
}
