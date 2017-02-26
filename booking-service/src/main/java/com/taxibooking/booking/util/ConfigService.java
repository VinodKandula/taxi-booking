package com.taxibooking.booking.util;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Represents a configuration service.
 *
 * @author vinodkandula
 */
public class ConfigService {
	
    private static final Logger LOGGER = Logger.getLogger(
            ConfigService.class.getName());

    private ConfigService() {
    }

     /**
     * Tokenise a property token with a provided value. e.g.
     * api.endpoint=http://127.0.0.1/api/{id} becomes http://127.0.0.1/api/10
     *
     * @param property property value to parse.
     * @param tokens tokens to value map.
     * @return property with values replaced by tokens.
     */
    public static String parseProperty(String property, Map<String, String> tokens) {
        
            if (property == null || tokens == null || tokens.isEmpty()) {
            throw new IllegalArgumentException();
        }

   
        return new URLTokenParser(tokens).tokenize(property);
    }
}
