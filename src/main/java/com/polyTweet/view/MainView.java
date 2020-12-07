package com.polyTweet.view;

import com.polyTweet.controller.ProfileController;
import com.polyTweet.controller.ScreenController;
import com.polyTweet.profile.Profile;
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

        try {
            controllerMap = new HashMap<>();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            VBox loginPane = loader.load();
            controllerMap.put("login", loader.getController());

            loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            VBox registerPane = loader.load();
            controllerMap.put("register", loader.getController());

            Scene scene = new Scene(loginPane);

            screenController = new ScreenController(scene);

            screenController.addScreen("login", loginPane);
            screenController.addScreen("register", registerPane);

            window.setTitle("PolyTweet");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
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

        try {
            FXMLLoader loader = new FXMLLoader(MainView.class.getResource("/fxml/profile.fxml"));
            BorderPane profilePane = loader.load();
            controllerMap.put("profile", loader.getController());

            loader = new FXMLLoader(MainView.class.getResource("/fxml/actualities.fxml"));
            BorderPane actualitiesPane = loader.load();
            controllerMap.put("actualities", loader.getController());

            loader = new FXMLLoader(MainView.class.getResource("/fxml/settings.fxml"));
            BorderPane settingsPane = loader.load();
            controllerMap.put("settings", loader.getController());

            screenController.addScreen("profile", profilePane);
            screenController.addScreen("actualities", actualitiesPane);
            screenController.addScreen("settings", settingsPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
