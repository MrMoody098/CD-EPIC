package com.cd.quizwhiz.userstuff;

import com.cd.quizwhiz.stats.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/*REFACTORING
Encapsulation: Made the username and currentScore fields private to encapsulate the class's state.

Constructor Documentation: Added proper Javadoc documentation for the constructor.

Error Handling: Improved error handling for file operations and parsing scores.
*/


/**
 * The `User` class represents a user in the QuizWhiz application and provides
 * methods
 * for managing user data and statistics.
 */
public class User {
    private final String username;
    private int currentScore;
    private final Logger logger = LoggerFactory.getLogger(User.class);

    public User(String username) {
        /**
         * Initializes a new user with the given username.
         *
         * @param username The username for the new user.
         */
        this.username = username;
    }

    public void addScore() {
        /**
         * Increases the user's current score by 1, typically used when a user gets an
         * answer correct.
         */
        currentScore++;
    }

    public int finalScore() {
        /**
         * Gets the player's current and final score for the game, saves it to their
         * user file,
         * and resets the score back to zero for the next game.
         *
         * @return The final score for the game.
         */
        String userDataFileName = username + ".txt";
        File userFile = new File(Auth.USER_FOLDER, userDataFileName);
        int finalScore = 0;

        if (userFile.exists()) {
            try (FileWriter writer = new FileWriter(userFile, true)) {
                writer.write("\n" + currentScore);
                finalScore = currentScore;
                currentScore = 0;
            } catch (IOException e) {
                logger.error("Error writing user score to file", e);
            }
        }
        return finalScore;
    }

    public double[] returnScores() {
        /**
         * Returns an array of the user's scores stored in their user file.
         *
         * @return An array of user scores as doubles.
         */
        List<Double> scoresList = new ArrayList<>();

        String userDataFileName = username + ".txt";
        File userFile = new File(Auth.USER_FOLDER, userDataFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line = reader.readLine(); // Read and discard the first line as it is our password.

            while ((line = reader.readLine()) != null) {
                double score = Double.parseDouble(line);
                scoresList.add(score);
            }
        } catch (IOException | NumberFormatException e) {
            logger.error("Error reading user scores from file", e);
        }

        double[] scoresArray = new double[scoresList.size()];
        for (int i = 0; i < scoresList.size(); i++) {
            scoresArray[i] = scoresList.get(i);
        }

        return scoresArray;
    }

    public double getMean() {
        /**
         * Calculates and returns the mean (average) of the user's scores.
         *
         * @return The mean of the user's scores.
         */
        double[] scores = returnScores();
        return Statistics.mean(scores);
    }

    public double getMedian() {
        /**
         * Calculates and returns the median of the user's scores.
         *
         * @return The median of the user's scores.
         */
        double[] scores = returnScores();
        return Statistics.median(scores);
    }

    public double getDeviation() {
        /**
         * Calculates and returns the standard deviation of the user's scores.
         *
         * @return The standard deviation of the user's scores.
         */
        double[] scores = returnScores();
        return Statistics.standardDeviation(scores);
    }

    public String getUsername() {
        /**
         * Gets the username of the user.
         *
         * @return The username of the user.
         */
        return username;
    }
}
