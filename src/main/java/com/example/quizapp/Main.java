package com.example.quizapp;

import com.example.quizapp.data.DatabaseSeeder;
import com.example.quizapp.utils.SceneManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class that initialises the application
 */
public class Main extends Application {

    public static final String TITLE = "AI Quiz Master";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 550;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Register the primary stage with SceneManager
        SceneManager.setPrimaryStage(primaryStage);

        // Optional: Use SceneManager to switch to WelcomePage scene
        SceneManager.switchScene("/com/example/quizapp/WelcomePage.fxml", TITLE);

        //For testing purposes: seed the database
        new DatabaseSeeder().seed();
    }

    public static void main(String[] args) {
        launch();
    }
}
