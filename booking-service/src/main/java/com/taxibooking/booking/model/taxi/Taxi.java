package com.taxibooking.booking.model.taxi;

import java.io.Serializable;
import java.util.Observable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.taxibooking.booking.model.Account;
import com.taxibooking.booking.model.Location;
import com.taxibooking.booking.model.Vehicle;
import com.taxibooking.booking.model.mapping.JpaTaxiStateDataConverter;
import com.taxibooking.booking.model.mapping.JsonTaxiStateDataConverter;

/**
 * Represents a Taxi.
 *
 * @author vinodkandula
 */
@Entity
@Table(name = "TAXI")
@NamedQueries({
    @NamedQuery(
            name = "Taxi.findTaxiForDriver",
            query = "SELECT t FROM Taxi t WHERE t.account.username = :username"
    ),
    @NamedQuery(
            name = "Taxi.findTaxisWithState",
            query = "SELECT t FROM Taxi t WHERE t.state = :state"
    )
})
public class Taxi extends Observable implements Serializable {
    
    @Transient
    private static final long serialVersionUID = -3300934288127984894L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "VEHICLE_ID")
    private Vehicle vehicle;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    // Last known taxi location.
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "TAXI_LOCATION_ID")
    private Location location;

    @Column(name = "TAXI_STATE")
    @Convert(converter = JpaTaxiStateDataConverter.class)
    private TaxiState state;

    // taxi states
    @JsonIgnore
    @Transient
    private static TaxiState offDutyTaxiState = new OffDutyTaxiState();
    
    @JsonIgnore
    @Transient
    private static TaxiState acceptedJobTaxiState = new AcceptedJobTaxiState();
    
    @JsonIgnore
    @Transient
    private static TaxiState onDutyTaxiState = new OnDutyTaxiState();
    
    
    public Taxi() {
        // Empty constructor required by JPA.
    }

    /**
     *
     * @param vehicle the taxis vehicle.
     * @param account the driver of the taxi.
     */
    public Taxi(Vehicle vehicle, Account account) {
        this.vehicle = vehicle;
        this.account = account;

        this.location = null;
        
        this.state = Taxi.getOffDutyTaxiState();
    }

    /**
     * Return flat rate cost of the taxi, excluding additional costs due to
     * millage.
     *
     * @return
     */
    @JsonIgnore
    public double getCostPerMile() {
        return this.getVehicle().getCostPerMile();
    }

    /**
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    /**
     * @param vehicle the vehicle to set
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return this.account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

     /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Return true if taxi has enough seats else false.
     *
     * @param numberSeats number of seats required.
     * @return true if vehicle has enough free seats.
     */
    public boolean checkseatAvailability(int numberSeats) {
        return this.vehicle.getNumberSeats() - 1 - numberSeats >= 0;
    }

    /**
     * Update taxi's current location.
     *
     * @param location new location.
     */
    public void updateLocation(Location location) {
        if (location != null) {
            // location can be null.
            this.location = new Location();
            this.location.setLatitude(location.getLatitude());
            this.location.setLongitude(location.getLongitude());
        } else {
            throw new IllegalArgumentException("Location can not be null.");
        }
    }

    /**
     * @return the state
     */
    @JsonSerialize(using = JsonTaxiStateDataConverter.class)
    public TaxiState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(TaxiState state) {
        this.state = state;
    }
    
   public void goOffDuty(){
       this.state.goOffDuty(this);
               
   }

    public void goOnDuty(){
        this.state.goOnDuty(this);
              
    }

    public void acceptJob(){
        this.state.acceptJob(this);
    }
     
    /**
     * @return the offDutyTaxiState
     */
    public static TaxiState getOffDutyTaxiState() {
        return Taxi.offDutyTaxiState;
    }

    /**
     * @return the acceptedJobTaxiState
     */
    public static TaxiState getAcceptedJobTaxiState() {
        return Taxi.acceptedJobTaxiState;
    }

    /*
     * @return the onDutyTaxiState
     */
    public static TaxiState getOnDutyTaxiState() {
        return Taxi.onDutyTaxiState;
    }
}
