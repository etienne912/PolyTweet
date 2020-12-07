package com.polyTweet.view;

import com.polyTweet.controller.ProfileController;
import com.polyTweet.controller.ScreenController;
import com.polyTweet.profile.Profile;
import com.polyTweet.serialization.Deserialization;
import com.polyTweet.serialization.Serialization;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class MainView extends Application {

    private static HashMap<String, Object> controllerMap;
    private static ScreenController screenController;
    private static Profile profileModel;
    private static Stage window;

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        profileModel = (Profile) Deserialization.deserialize("./tmp/profile.ser");

//        profileModel = new Profile("Étienne", "Lécrivain");

        if (profileModel != null) {

            try {
                controllerMap = new HashMap<>();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
                VBox loginPane = loader.load();
                controllerMap.put("login", loader.getController());

                loader = new FXMLLoader(getClass().getResource("/fxml/profile.fxml"));
                BorderPane profilePane = loader.load();
                controllerMap.put("profile", loader.getController());

                loader = new FXMLLoader(getClass().getResource("/fxml/actualities.fxml"));
                BorderPane actualitiesPane = loader.load();
                controllerMap.put("actualities", loader.getController());

                loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
                BorderPane settingsPane = loader.load();
                controllerMap.put("settings", loader.getController());

                Scene scene = new Scene(loginPane);

                screenController = new ScreenController(scene);

                screenController.addScreen("login", loginPane);
                screenController.addScreen("profile", profilePane);
                screenController.addScreen("actualities", actualitiesPane);
                screenController.addScreen("settings", settingsPane);

                window.setTitle("PolyTweet");
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Error : File not found Exception");
        }

    }

    public static Profile getProfile() {
        return profileModel;
    }

    public static void switchScene(String name) {
        screenController.activate(name);
    }

    public static void closeWindow() {
        Serialization.serialize(profileModel, "./tmp/profile.ser");
        window.close();
    }

    public static void updateProfile() {
        ProfileController profileController = (ProfileController) controllerMap.get("profile");
        profileController.update();
    }

    public static Stage getPrimaryStage() {
        return window;
    }

    public static void setProfile(Profile profile) {
        profileModel = profile;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
