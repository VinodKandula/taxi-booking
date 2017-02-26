package com.taxibooking.booking.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Utility class for HTTP.
 *
 * @author vinodkandula
 */
public class HttpUtils {

    private HttpUtils() {
    }

    /**
     * Return a JSONObject representing the URL response.
     *
     * @param url url to query.
     * @return a JSON object containing the URL response.
     * @throws JSONException json invalid.
     */
    public static JSONObject getUrl(String url) throws JSONException {
        try {
            InputStream is = new URL(url).openStream();

            String data = "";
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String inputLine = "";

                while ((inputLine = reader.readLine()) != null) {
                    data += inputLine;
                }
            }

            return new JSONObject(data);

        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * URL encoded the provided value.
     *
     * @param value the value to URL encode.
     * @return the URL encoded string.
     */
    public static String stringEncode(String value) {
        return value.replaceAll(" ", "%20");
    }
}
