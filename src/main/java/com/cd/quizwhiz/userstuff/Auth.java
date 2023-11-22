package com.cd.quizwhiz.userstuff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static com.cd.quizwhiz.userstuff.PasswordEncryption.encrypt;
import static com.cd.quizwhiz.userstuff.PasswordEncryption.verifyPassword;
/*REFACTORING
Constant Naming: Changed userFolder to USER_FOLDER to follow the naming convention for constants.

Error Handling: Moved the encryption process to a separate method (encryptPassword)
to handle potential errors more gracefully.

*/


/**
 * The Auth class handles user registration and login
 * for a quiz application. It allows users to register
 * with a username and password and then verify their credentials during login.
 */
public class Auth {
    // Directory where user data files are stored.
    public static final String USER_FOLDER = "users";
    private static final Logger logger = LoggerFactory.getLogger(Auth.class);

    public static String register(String username, String password) {
        String userDataFileName = username + ".txt";
        File userFile = new File(USER_FOLDER, userDataFileName);

        if (!userFile.exists()) {
            try {
                FileWriter writer = new FileWriter(userFile);

                String encryptedPassword = encryptPassword(password);
                if (encryptedPassword == null) {
                    return "Error encrypting password.";
                }

                writer.write(encryptedPassword);
                writer.close();
                return username; // Registration successful.
            } catch (IOException e) {
                logger.error("IOException during file operations", e);
                return "Error creating user file.";
            } catch (Exception e) {
                logger.error("Unexpected error during registration", e);
                throw new RuntimeException("Unexpected error occurred during registration", e);
            }
        } else {
            return "Username already exists."; // Username is already taken.
        }
    }

    private static String encryptPassword(String password) {
        try {
            return encrypt(password);
        } catch (Exception e) {
            logger.error("Error encrypting password", e);
            return null;
        }
    }

    public static boolean login(String username, String password) {
        boolean loggedIn = false;

        String userDataFileName = username + ".txt";
        File userFile = new File(USER_FOLDER, userDataFileName);

        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line = br.readLine();
            if (line != null) {
                String storedPassword = line;
                if (verifyPassword(password, storedPassword)) {
                    loggedIn = true;
                }
            }
        } catch (FileNotFoundException e) {
            // Handle the file not found exception.
            loggedIn = false;
        } catch (IOException e) {
            // Handle other I/O exceptions.
            loggedIn = false;
        } catch (Exception e) {
            throw new RuntimeException("Error during login", e);
        }

        return loggedIn;
    }
}
