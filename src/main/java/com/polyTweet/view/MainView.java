package com.polyTweet.view;

import com.polyTweet.controller.ActualitiesController;
import com.polyTweet.controller.ProfileController;
import com.polyTweet.controller.ProfileVisitorController;
import com.polyTweet.controller.ScreenController;
import com.polyTweet.node.Node;
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
    private static Profile profileModel, profilVisitor;
    private static Node profileNode;
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

    public static Profile getProfileVisitor() {
        return profilVisitor;
    }

    public static Node getNode() {
        return profileNode;
    }

    public static void switchScene(String name) {
        screenController.activate(name);
    }

    public static void closeWindow() {
        profileNode.close();
        Serialization.serialize(profileModel, "./tmp/profile" + profileModel.getFirstName() + ".ser");
        switchScene("login");
    }

    public static void update() {
        ProfileController profileController = (ProfileController) controllerMap.get("profile");
        ActualitiesController actualitiesController = (ActualitiesController) controllerMap.get("actualities");
        ProfileVisitorController profileVisitorController = (ProfileVisitorController) controllerMap.get("profileVisitor");

        profileController.update();
        actualitiesController.update();
        profileVisitorController.update();
    }

    public static Stage getPrimaryStage() {
        return window;
    }

    private static void loadViews() {
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

    public static void loadVisitorView() {
        try {
            FXMLLoader loader = new FXMLLoader(MainView.class.getResource("/fxml/profileVisitor.fxml"));
            BorderPane profileVisitorPane = loader.load();
            controllerMap.put("profileVisitor", loader.getController());

            screenController.addScreen("profileVisitor", profileVisitorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initVisitProfile(Profile profile) {
        profilVisitor = profile;

        loadVisitorView();
    }

    public static void init(Profile profile, Node node) {
        profileModel = profile;
        profileNode = node;

        loadViews();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
