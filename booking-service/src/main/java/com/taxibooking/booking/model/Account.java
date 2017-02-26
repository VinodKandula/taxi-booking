 package com.taxibooking.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Entity class for a user.
 *
 * @author vinodkandula
 */
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {
    
    @Transient
    private static final Logger LOGGER = Logger.getLogger(Account.class.getName());
    
    @Transient
    private static final long serialVersionUID = 1998061924459270142L;

    @Id
    @Column(name = "USERNAME")
    private String username;

    @NotNull
    @Column(name = "COMMON_NAME", length = 128)
    private String commonName;
    
    @NotNull
    @Column(name = "FAMILY_NAME", length = 128)
    private String familyName;

    @NotNull
    @Column(name = "PASSWORD", length = 256)
    private String password;

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @Column(name = "PHONE_NUMBER", length = 15)
    private String phoneNumber;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @Column(name = "ACTIVE")
    @Enumerated(EnumType.STRING)
    private AccountStatus active;
    
    @Column(name = "GOOGLE_GCM_REG_ID", nullable = true)
    private String gcmRegId;
    
    public Account() {
        // Empty as per JPA 2.0 specification.
    }

    /**
     * Constructor for account.
     *
     * @param username username.
     * @param commonName name.
     * @param familyName familyName;
     * @param password password hash.
     * @param phoneNumber phone number.
     * @param email email.
     */
    public Account(String username, String commonName, String familyName, String password, String phoneNumber, String email) {
        this.username = username;
        this.commonName = commonName;
        this.familyName = familyName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;

        this.setActive();
    } 

    /**
     * Return true if provided password matches hash else false.
     *
     * @param password password to check.
     * @return true if password matches, else false.
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * @return user user's username
     */
    public String getUsername() {
        return this.username;
    }
    
    /**
     * Set users username.
     * 
     * @param username username to set.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Check if a user has a specified role.
     *
     * @param role role to check. Role can be provided in upper or lowercase.
     * @return true if user has role else false.
     */
    public boolean hasRole(String role) {
        try {
            return this.role == AccountRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.FINEST, null, ex);
            return false;
        }
    }

    /**
     * Return user password;
     *
     * @return user password;
     */
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    /**
     * Set user password.
     *
     * @param password password.
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return user's email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user's email.
     *
     * @param email the email to set
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the role
     */
    public AccountRole getRole() {
        return role;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @param role the role to set
     */
    public void setRole(AccountRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }

        this.role = role;
    }

    /**
     * @return Return true if active, else false.
     */
    public boolean isActive() {
        return this.active.ACTIVE == AccountStatus.ACTIVE;
    }

    /**
     * Set account inactive.
     */
    public void setInActive() {
        this.active = AccountStatus.ACTIVE;
    }

    /**
     * Set account active.
     */
    public final void setActive() {
        this.active = AccountStatus.ACTIVE;
    }

    /**
     * @return the name
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * @param commonName the name to set
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     * @return family name.
     */
    public String getFamilyName() {
        return familyName;
    }
    
    /**
     * Get Google Cloud messenger registration id.
     * @return the user's Google cloud messenger registration id.
     */
    public String getGcmRegId() {
        return gcmRegId;
    }

    /**
     * Set users Google cloud messenger registration id.
     * @param gcmRegId Google cloud messenger registration id.
     */
    public void setGcmRegId(String gcmRegId) {
        this.gcmRegId = gcmRegId;
    }

    /**
     * Set family name.
     * 
     * @param familyName the family name to set.
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    
    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.commonName, other.commonName)) {
            return false;
        }
        if (!Objects.equals(this.familyName, other.familyName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (this.role != other.role) {
            return false;
        }
        if (this.active != other.active) {
            return false;
        }
        return true;
    }
}
