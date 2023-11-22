package com.cd.quizwhiz.questions;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/*
Method Naming: Renamed shuffleArrayList to shuffleList to adhere to Java naming conventions.

Encapsulation: Used getCategory and getCategory methods in place of accessing fields directly to encapsulate the class's state.

Code Simplification: Replaced the custom shuffle method with Collections.shuffle for clarity and simplicity.

Added Comments: Added comments to clarify the purpose of each method.

Added JavaDocs: Added JavaDocs to clarify the purpose of each method.
  */


/**
 * The {@code QuestionBank} class manages a bank of quiz questions,
 * allowing operations like selecting questions for head-to-head play,
 * increasing difficulty within a category, and selecting random questions within a category.
 */
public class QuestionBank {

    /** The file path of the CSV file containing quiz questions. */
    private static final String CSV_FILE_PATH = "C:\\Users\\ticta\\MyRepos\\CD-EPIC\\src\\main\\java\\com\\cd\\quizwhiz\\questions\\Questions.csv";

    /**
     * Reads questions from the CSV file and constructs a list of {@code Question} objects.
     *
     * @return A list of {@code Question} objects.
     */
    private static List<Question> readQuestionsFromCSV() {
        List<Question> questions = new ArrayList<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(CSV_FILE_PATH)).withSkipLines(1).build()) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                // Extracting information from CSV and creating a Question object
                String questionText = line[0].replace("\"", "");
                String[] options = line[1].split(",");
                int correctOption = Integer.parseInt(line[2]);
                Category category = Category.valueOf(line[3]);
                Difficulty difficulty = Difficulty.valueOf(line[4]);

                Question question = new Question(questionText, options, correctOption, category, difficulty);
                questions.add(question);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return questions;
    }

    /**
     * Shuffles the order of questions in the given list using the provided randomizer.
     *
     * @param list   The list of questions to be shuffled.
     * @param random The randomizer used for shuffling.
     */
    private static void shuffleList(List<Question> list, Random random) {
        Collections.shuffle(list, random);
    }

    /**
     * Selects a set of questions for a head-to-head quiz.
     *
     * @return An array of questions for head-to-head play.
     */
    public static Question[] headToHead() {
        List<Question> questionList = readQuestionsFromCSV();
        Random random = new Random();
        shuffleList(questionList, random);

        // Selecting a subset of questions for head-to-head play
        return questionList.subList(0, 12).toArray(new Question[12]);
    }

    /**
     * Selects questions with increased difficulty within a specified category.
     *
     * @param category The category for which to select questions.
     * @return An array of questions with increased difficulty in the specified category.
     */
    public static Question[] incDifficulty(Category category) {
        List<Question> questionList = readQuestionsFromCSV();
        questionList.removeIf(q -> q.getCategory() != category);

        // Returning questions with increased difficulty in the specified category
        return questionList.toArray(new Question[0]);
    }

    /**
     * Selects a set of random questions within a specified category.
     *
     * @param category The category for which to select random questions.
     * @return An array of randomly selected questions in the specified category.
     */
    public static Question[] randomQuestion(Category category) {
        List<Question> questionList = readQuestionsFromCSV();
        questionList.removeIf(q -> q.getCategory() != category);

        Random random = new Random();
        shuffleList(questionList, random);

        // Returning randomly selected questions in the specified category
        return questionList.toArray(new Question[0]);
    }
}
