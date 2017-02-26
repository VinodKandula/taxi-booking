package com.taxibooking.booking.model.mapping;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.taxibooking.booking.model.taxi.TaxiState;

/**
 * JSON serializer Taxi booking state class.
 * 
 * @author vinodkandula
 */
public class JsonTaxiStateDataConverter extends JsonSerializer<TaxiState> {

    @Override
    public void serialize(TaxiState t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeString(new JpaTaxiStateDataConverter().convertToDatabaseColumn(t));
    }
}
