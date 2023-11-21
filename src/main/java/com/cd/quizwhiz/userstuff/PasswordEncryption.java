import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHashing {
    /**
     * Hashes a password using SHA-256.
     *
     * @param password The password to be hashed.
     * @return The hashed password as a Base64 encoded string.
     * @throws NoSuchAlgorithmException if the hashing algorithm is not available.
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes());
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
        String enteredPasswordHash = hashPassword(enteredPassword);
        return enteredPasswordHash.equals(storedHashedPassword);
    }
}
