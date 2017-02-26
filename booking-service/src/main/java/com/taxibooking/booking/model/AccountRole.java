package com.taxibooking.booking.model;

/**
 * Enum to represent account role.
 *
 * @author vinodkandula
 */
public enum AccountRole {

    DRIVER("driver"),
    PASSENGER("passenger");

    private final String role;

    AccountRole(final String role) {
        this.role = role;
    }

    /**
     * Return true if valid role else false.
     *
     * @param role role to check.
     * @return true if valid role else false.
     */
    public static AccountRole getRole(String role) {
        try {
            return AccountRole.valueOf(role);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    @Override
    public String toString() {
        return this.role;
    }
}
