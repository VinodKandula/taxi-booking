package com.taxibooking.booking.model.mapping;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.taxibooking.booking.model.booking.BookingState;

/**
 * JSON serializer for Booking state class.
 *
 * @author vinodkandula
 */
public class JsonBookingStateDataConverter extends JsonSerializer<BookingState> {

    @Override
    public void serialize(BookingState t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeString(new JpaBookingStateDataConverter().convertToDatabaseColumn(t));
    }

}
