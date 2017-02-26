package com.taxibooking.booking.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * Class represents a DataMapper singleton.
 *
 * @author vinodkandula
 */
public class DataMapper extends ObjectMapper {

    private static volatile DataMapper dataMapper;

    private DataMapper() {
        // private as singleton
    }

    /**
     * Return an instance of data mapper. 
     * If it is null create a new instance.
     *
     * @return a instance of data mapper.
     */
    public static DataMapper getInstance() {
        if (DataMapper.dataMapper == null) {
            synchronized (DataMapper.class) {
                DataMapper.dataMapper = new DataMapper();
                DataMapper.dataMapper.setSerializationInclusion(Include.NON_NULL);
                DataMapper.dataMapper.setPropertyNamingStrategy(
                        PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            }
        }
        return DataMapper.dataMapper;
    }

    public String getObjectAsJson(Object obj) {
        try {
            return this.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DataMapper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
