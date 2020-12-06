package com.polyTweet.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileView extends Application {

    @Override
    public void start(final Stage primaryStage) {
        try {
            final Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
            primaryStage.setScene(new Scene(root, 300, 250));
        } catch (IOException ex) {
            System.err.println("Erreur au chargement: " + ex);
        }

        primaryStage.setTitle("Profile");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}