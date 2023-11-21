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

public class QuestionBank {

    private static final String CSV_FILE_PATH = "C:\\Users\\ticta\\MyRepos\\CD-EPIC\\src\\main\\java\\com\\cd\\quizwhiz\\questions\\Questions.csv";

    private static List<Question> readQuestionsFromCSV() {
        List<Question> questions = new ArrayList<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(CSV_FILE_PATH)).withSkipLines(1).build()) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
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

    public static Question[] headToHead() {
        List<Question> questionList = readQuestionsFromCSV();
        Random random = new Random();
        shuffleArrayList(questionList, random);

        return questionList.subList(0, 12).toArray(new Question[12]);
    }

    public static Question[] incDifficulty(Category category) {
        List<Question> questionList = readQuestionsFromCSV();
        questionList.removeIf(q -> q.category != category);

        return questionList.toArray(new Question[0]);
    }

    public static Question[] randomQuestion(Category category) {
        List<Question> questionList = readQuestionsFromCSV();
        questionList.removeIf(q -> q.category != category);

        Random random = new Random();
        shuffleArrayList(questionList, random);

        return questionList.toArray(new Question[0]);
    }

    public static void shuffleArrayList(List<Question> list, Random random) {
        int n = list.size();

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            // Swap elements at indices i and j
            Question temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }
}
