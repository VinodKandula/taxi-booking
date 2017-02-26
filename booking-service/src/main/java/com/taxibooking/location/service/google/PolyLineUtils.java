package com.taxibooking.location.service.google;

import java.util.ArrayList;
import java.util.List;

import com.taxibooking.booking.model.Location;

/**
 * Utility class for PolyLines
 *
 * @author vinodkandula
 */
public class PolyLineUtils {

    private PolyLineUtils() {
    }

    /**
     * Google polyline decoder Algorithm modified Source (Jeffrey Sambells)
     * http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * Google decode algorithm:
     * https://developers.google.com/maps/documentation/utilities/polylinealgorithm?csw=1
     *
     * @param encoded the polyline encoded string.
     * @return a collection of locations representing a polyline path.
     */
    public static List<Location> decodePoly(String encoded) {

        List<Location> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;

            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            Location p = new Location((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
