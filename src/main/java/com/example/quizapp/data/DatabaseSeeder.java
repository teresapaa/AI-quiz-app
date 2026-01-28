package com.example.quizapp.data;

import com.example.quizapp.Models.*;
import com.example.quizapp.utils.AuthManager;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/*
This class is for initializing the database. It adds two initial users, alice and bob, but does not
remove users created by you. Other tables are cleaned and initialized in every function call.
 */

public class DatabaseSeeder {

    private final Connection connection;
    private final SqliteUserDAO userDAO;
    private final SqliteQuizDAO quizDAO;
    private final SqliteQuestionDAO questionDAO;
    private final SqliteQuizAttemptDAO quizAttemptDAO;
    private final AuthManager authManager;

    public DatabaseSeeder() {
        this.connection = SqliteConnection.getInstance();
        this.userDAO = new SqliteUserDAO();
        this.quizDAO = new SqliteQuizDAO();
        this.questionDAO = new SqliteQuestionDAO();
        this.quizAttemptDAO = new SqliteQuizAttemptDAO();
        this.authManager = AuthManager.getInstance();
    }

    public void seed() {
        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("DELETE FROM quizAttempts");

            User alice = userDAO.getUserByEmail("alice@example.com");
            if (alice == null) {
                userDAO.addUser(new User("alice", "alice@example.com", authManager.hashPassword("password1")));
                alice = userDAO.getUserByEmail("alice@example.com");
            }

            User bob = userDAO.getUserByEmail("bob@example.com");
            if (bob == null) {
                userDAO.addUser(new User("bob", "bob@example.com", authManager.hashPassword("password2")));
                bob = userDAO.getUserByEmail("bob@example.com");
            }

            int aliceID = alice.getUserID();
            int bobID = bob.getUserID();

            //If quiz does not exist in database yet, adds it
            Quiz quiz1 = quizDAO.getQuizByName("Algebra Basics");
            if (quiz1 == null) {
                quizDAO.addQuiz(new Quiz("Algebra Basics", "Mathematics","Algebra", 180, "Easy", "Year 10", "Australia", "Public", aliceID));
                quiz1 = quizDAO.getQuizByName("Algebra Basics");
                //Add same question 5 times
                for (int i = 1; i <= 5; i++) {
                    int quiz1ID = quiz1.getQuizID();
                    questionDAO.addQuestion(new Question(quiz1ID, "What is 2 + " + i + "?", "" + (2 + i), "" + (2 + i + 1), "" + (2 + i - 1), "" + (2 * i)));
                }
            }

            Quiz quiz2 = quizDAO.getQuizByName("Photosynthesis");
            if (quiz2 == null) {
                quizDAO.addQuiz(new Quiz("Photosynthesis", "Science","Biology", 180, "Medium", "Year 11", "Australia", "Public", bobID));
                quiz2 = quizDAO.getQuizByName("Photosynthesis");
                for (int i = 1; i <= 5; i++) {
                    int quiz2ID = quiz2.getQuizID();
                    questionDAO.addQuestion(new Question(quiz2ID, "What is produced in photosynthesis step " + i + "?", "Oxygen", "Carbon Dioxide", "Water", "Glucose"));
                }
            }

            Quiz quiz3 = quizDAO.getQuizByName("World War II");
            if (quiz3 == null) {
                quizDAO.addQuiz(new Quiz("World War II", "History","Wars", 180, "Easy", "Year 10", "Australia", "Public", aliceID));
                quiz3 = quizDAO.getQuizByName("World War II");
                for (int i = 1; i <= 5; i++) {
                    int quiz3ID = quiz3.getQuizID();
                    questionDAO.addQuestion(new Question(quiz3ID, "Who led Germany in WW2 (Q" + i + ")?", "Hitler", "Stalin", "Churchill", "Roosevelt"));
                }
            }

            //Get the quiz ID after database autoincrements it
            int quiz1ID = quizDAO.getQuizByName("Algebra Basics").getQuizID();
            int quiz2ID = quizDAO.getQuizByName("Photosynthesis").getQuizID();
            int quiz3ID = quizDAO.getQuizByName("World War II").getQuizID();

            List<String> AlgebraAnswers = Arrays.asList("3", "4", "5", "6", "7");

            List<String> HistoryAnswers =  Arrays.asList("Hitler", "Hitler", "Hitler", "Hitler", "Hitler");

            List<String> BiologyAnswers = Arrays.asList("Water", "Water", "Water", "Water", "Water");

            quizAttemptDAO.addQuizAttempt(new QuizAttempt(quiz1ID, aliceID, 100, 180, AlgebraAnswers));
            quizAttemptDAO.addQuizAttempt(new QuizAttempt(quiz2ID, aliceID, 20, 180, BiologyAnswers));
            quizAttemptDAO.addQuizAttempt(new QuizAttempt(quiz3ID, aliceID, 20, 180, HistoryAnswers));
            quizAttemptDAO.addQuizAttempt(new QuizAttempt(quiz1ID, aliceID, 100, 180, AlgebraAnswers));

            quizAttemptDAO.addQuizAttempt(new QuizAttempt(quiz1ID, bobID, 100, 180, AlgebraAnswers));
            quizAttemptDAO.addQuizAttempt(new QuizAttempt(quiz2ID, bobID, 20, 180, BiologyAnswers));
            quizAttemptDAO.addQuizAttempt(new QuizAttempt(quiz3ID, bobID, 20, 180, HistoryAnswers));
            quizAttemptDAO.addQuizAttempt(new QuizAttempt(quiz3ID, bobID, 20, 180, HistoryAnswers));

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
