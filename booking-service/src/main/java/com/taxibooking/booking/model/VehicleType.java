package com.taxibooking.booking.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represent a vehicle type.
 *
 * @author vinodkandula
 */
@Entity
@Table(name = "VEHICLE_TYPE")
public class VehicleType implements Serializable {
    
    @Transient
    private static final long serialVersionUID = 6556730065381319372L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "COST_PER_MILE")
    private double costPerMile;

    public VehicleType() {
        // Empty constructor required by JPA.
    }

    /**
     * Default constructor for vehicle type class.
     * 
     * @param name name of vehicle.
     * @param manufacturer vehicle manufacturer.
     * @param model vehicle model.
     * @param costPerMile cost per mile.
     */
    public VehicleType(String name, String manufacturer, String model, double costPerMile) {
        this.name = name;
        this.costPerMile = costPerMile;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cost per mile.
     */
    public double getCostPerMile() {
        return this.costPerMile;
    }

    /**
     * @param costPerMile the cost to set
     */
    public void setCostPerMile(int costPerMile) {
        this.costPerMile = costPerMile;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }
}
