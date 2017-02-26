package com.taxibooking.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represent an address. Address has a location and address name.
 *
 * @author vinodkandula
 */
@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {
    
    @Transient
    private static final long serialVersionUID = 1833058791696309083L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Column(name = "STREET_ADDRESS")
    private String streetAddress;

    public Address() {
        // Needed by JPA.
    }

    /**
     * Constructor for class address.
     *
     * @param streetAddress address the street address e.g. 30 Cheviots, Hatfield, AL10 8JD.
     * @param location latitude and longitude location of address.
     */
    public Address(String streetAddress, Location location) {
        this.streetAddress = streetAddress;
        this.location = location;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the street address.
     */
    public String getAddress() {
        return streetAddress;
    }

    /**
     * @param streetAddress the address to set
     */
    public void setAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * @return record id.
     */
    @JsonIgnore
    public Long getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id id.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
