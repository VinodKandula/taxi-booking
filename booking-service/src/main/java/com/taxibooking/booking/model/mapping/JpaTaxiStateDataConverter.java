package com.taxibooking.booking.model.mapping;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.taxibooking.booking.model.taxi.AcceptedJobTaxiState;
import com.taxibooking.booking.model.taxi.OffDutyTaxiState;
import com.taxibooking.booking.model.taxi.OnDutyTaxiState;
import com.taxibooking.booking.model.taxi.TaxiState;
import com.taxibooking.booking.model.taxi.TaxiStates;

/**
 *
 * @author vinodkandula
 */
@Converter
public class JpaTaxiStateDataConverter implements AttributeConverter<TaxiState, String> {

    @Override
    public String convertToDatabaseColumn(TaxiState attribute) {

        // use java reflection here instead?
        if (attribute instanceof AcceptedJobTaxiState) {
            return TaxiStates.ON_JOB.toString();

        } else if (attribute instanceof OffDutyTaxiState) {
            return TaxiStates.OFF_DUTY.toString();

        } else if (attribute instanceof OnDutyTaxiState) {
            return TaxiStates.ON_DUTY.toString();

        }

        throw new IllegalArgumentException("Cannot convert object to data.");
    }

    @Override
    public TaxiState convertToEntityAttribute(String dbData) {
        
        if(dbData.equals(TaxiStates.OFF_DUTY.toString())){
            return new OffDutyTaxiState();
        }else if(dbData.equals(TaxiStates.ON_DUTY.toString())){
            return new OnDutyTaxiState();
        }else if(dbData.equals(TaxiStates.ON_JOB.toString())){
            return new AcceptedJobTaxiState();
        }

        throw new IllegalArgumentException("Cannot convert database entity to object.");

    }
}
