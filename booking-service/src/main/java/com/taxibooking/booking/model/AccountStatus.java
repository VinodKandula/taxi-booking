package com.taxibooking.booking.model;

/**
 * State of an account.
 *
 * @author vinodkandula
 */
public enum AccountStatus {

    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private String status;

    AccountStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
