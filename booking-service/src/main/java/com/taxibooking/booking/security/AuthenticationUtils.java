package com.taxibooking.booking.security;

import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Authentication utility class provide functions for managing base64
 * encoding/decoding and hashing of user passwords.
 *
 * @author vinodkandula
 */
public class AuthenticationUtils {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationUtils.class.getName());

    private AuthenticationUtils() {
        //Empty as utility class.
    }

    /**
     * Hash a plaintext value using BCrypt, blowfish block-cipher with a work
     * factor of 12.
     *
     * @param password password to hash.
     * @return a string representation of the salted password.
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Check if plain text password matches hash.
     *
     * @param password plaintext password
     * @param hash hashed password
     * @return true if plaintext password and hash match, else false.
     */
    public static boolean checkPassword(String password, String hash) {

        if (password == null || hash == null) {
            IllegalArgumentException e = new IllegalArgumentException(
                    "Password and hash cannot be null");
            LOGGER.log(Level.WARNING, e.getMessage());
            throw e;
        }

        return BCrypt.checkpw(password, hash);
    }

    /**
     * Generate a random code.
     *
     * @param validCodeCharacters valid code characters
     * @param length length of code to generate. Length of password must be more
     * than 0.
     * @return a random code with the specified length
     */
    public static String generateCode(String validCodeCharacters, int length) {

        if (length == 0 || validCodeCharacters.length() == 0) {
            IllegalArgumentException e = new IllegalArgumentException(
                    "Password or validCodeCharacters length cannot be 0");
            LOGGER.log(Level.WARNING, e.getMessage());
            throw e;
        }

        Random rand = new Random();

        StringBuilder sBuilder = new StringBuilder();

        while (sBuilder.length() < length) {
            sBuilder.append(
                    validCodeCharacters.charAt(
                            rand.nextInt(
                                    validCodeCharacters.length())));
        }

        return sBuilder.toString();
    }

    /**
     * Generate code with default valid code characters.
     *
     * @param length length of code to generate
     * @return a random code with with the specified length
     */
    public static String generateCode(int length) {
        return AuthenticationUtils.generateCode(
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567", length);
    }

    /**
     * Return base64 encoded string.
     *
     * @param base64Encoding base64 encoding
     * @return base64 decoded string.
     * @throws IllegalArgumentException base64Encoding string is null.
     */
    public static String base64Decode(String base64Encoding) {

        if (base64Encoding == null) {
            throw new IllegalArgumentException();
        }

        return new String(Base64.getDecoder()
                .decode(base64Encoding.getBytes()));

    }
}
