/**
 * The Stats package contains classes for calculating statistical measures.
 */
package com.cd.quizwhiz.Stats;

import static java.util.Arrays.sort;

/**
 * The Statistics class contains methods for calculating statistical measures.
 */
public class Statistics {

    /**
     * Calculates the mean of an array of scores.
     *
     * @param scores An array of scores.
     * @return The mean of the scores.
     */
    public static double Mean(double[] scores) {
        double sum = 0;

        // Loop through the array using enhanced for loop and calculate the sum of all scores.
        for (double i : scores) {
            sum += i;
        }
        // Calculate and return the mean by dividing the sum by the number of scores.
        return sum / scores.length;
    }

    /**
     * Calculates the median of an array of scores.
     *
     * @param scores An array of scores.
     * @return The median of the scores.
     */
    public static double Median(double[] scores) {
        double median = 0;

        // Sort the scores in ascending order.
        sort(scores);

        // Check if the number of scores is even or odd to calculate the median accordingly.
        if (scores.length % 2 == 0) {
            // For an even number of scores, take the average of the two middle values.
            median = (scores[(scores.length / 2) - 1] + scores[scores.length / 2]) / 2.0;
        } else {
            // For an odd number of scores, simply take the middle value as the median.
            median = scores[scores.length / 2];
        }

        // Return the calculated median.
        return median;
    }

    /**
     * Calculates the standard deviation of an array of scores.
     *
     * @param scores An array of scores.
     * @return The standard deviation of the scores.
     */
    public static double StandardDeviation(double[] scores){
        //get mean
        double mean = Mean(scores);

        // calculate the standard deviation
        double standardDeviation = 0.0;

        for (double num : scores) {
            //  ∑                         ( Xi – ų ) ^ 2
            standardDeviation += Math.pow(num - mean, 2);
        }

        //square root of ( ∑ ( Xi – ų ) ^ 2 ) / N
        return Math.sqrt(standardDeviation / scores.length);
    }
}
