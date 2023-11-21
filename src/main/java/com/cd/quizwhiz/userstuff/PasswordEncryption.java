package com.cd.quizwhiz.userstuff;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * This class provides methods for password hashing using SHA-256.
 */
public class PasswordEncryption {

    /**
     * Hashes a password using SHA-256.
     *
     * @param password The password to be hashed.
     * @return The hashed password as a Base64 encoded string.
     * @throws NoSuchAlgorithmException if the hashing algorithm is not available.
     */
    public static String encrypt(String password) throws NoSuchAlgorithmException {
        // Create a MessageDigest instance using SHA-256 hashing algorithm
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Convert the password string to bytes and compute the hash
        byte[] hashBytes = digest.digest(password.getBytes());

        // Encode the hash bytes to Base64 and return the result
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    /**
     * Verifies a password against a hashed password.
     *
     * @param enteredPassword      The password entered by the user.
     * @param storedHashedPassword The stored hashed password.
     * @return true if the passwords match, false otherwise.
     * @throws NoSuchAlgorithmException if the hashing algorithm is not available.
     */
    public static boolean verifyPassword(String enteredPassword, String storedHashedPassword) throws NoSuchAlgorithmException {
        // Hash the entered password for comparison
        String enteredPasswordHash = encrypt(enteredPassword);

        // Compare the hashed entered password with the stored hashed password
        return enteredPasswordHash.equals(storedHashedPassword);
    }
}
