package com.taxibooking.booking.model;

/**
 * Enum to represent account types.
 * 
 * @author vinodkandula
 */
public enum AccountType {

    PASSENGER("passenger"),
    DRIVER("driver"),
    ADMIN("admin");

    String roleName;

    /**
     * @param roleName role name.
     */
    AccountType(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Return true if valid role else false.
     *
     * @param role role to check.
     * @return true if valid role else false.
     */
    public static boolean isValidRole(String role) {

        try {
            AccountType.valueOf(role);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.roleName;
    }
}
