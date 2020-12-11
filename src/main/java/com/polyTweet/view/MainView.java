package com.polyTweet.view;

import com.jfoenix.assets.JFoenixResources;
import com.polyTweet.controller.*;
import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
import com.polyTweet.utils.serialization.Serialization;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class to run to launch the software.
 */
public class MainView extends Application {

    private static HashMap<String, Object> controllerMap;
    private static ScreenController screenController;
    private static Profile myProfile, profilVisitor;
    private static Node myNode;
    private static Stage window;

    /**
     * Initialization of the software
     * @param primaryStage Main window of the software
     */
    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        try {
            controllerMap = new HashMap<>();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            VBox loginPane = loader.load();
            LoginController loginController = loader.getController();
            loginController.setVars(this);
            controllerMap.put("login", loginController);

            loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            BorderPane registerPane = loader.load();
            RegisterController registerController = loader.getController();
            registerController.setVars(this);
            controllerMap.put("register", registerController);

            Scene scene = new Scene(loginPane, 1000, 700);

            screenController = new ScreenController(scene);

            screenController.addScreen("login", loginPane);
            screenController.addScreen("register", registerPane);

            final ObservableList<String> stylesheets = scene.getStylesheets();
            stylesheets.addAll(
                    JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
                    JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
                    getClass().getResource("/css/application.css").toExternalForm());

            primaryStage.getIcons().add(new Image("/img/polytweet.png"));
            window.setResizable(false);
            window.setTitle("PolyTweet");
            window.setScene(scene);
            window.show();

            window.setOnCloseRequest(event -> disconnection());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Function to get user profile model.
     * @return User profile model
     */
    public static Profile getProfile() {
        return myProfile;
    }

    /**
     * Function to get visited profile model.
     * @return Visited profile model
     */
    public static Profile getProfileVisitor() {
        return profilVisitor;
    }

    /**
     * Function to get node model of the user profile.
     * @return Node model of the user profile
     */
    public static Node getNode() {
        return myNode;
    }

    /**
     * Function called to switch of view.
     * @param name Name of the view to display
     */
    public static void switchScene(String name) {
        screenController.activate(name);
    }

    /**
     * Function to save user profile, close the connection properly and disconnect him.
     */
    public static void disconnection() {
        if (myNode != null) {
            myNode.close();
            myNode = null;
        }
        if (myProfile != null) {
            myProfile.clearObservers();
            Serialization.serialize(myProfile, "./tmp/profile-" + myProfile.getFirstName() + ".ser");
            myProfile = null;
            switchScene("login");
        }
    }

    /**
     * Function to get this class.
     * @return This class
     */
    public static Stage getPrimaryStage() {
        return window;
    }

    /**
     * Function called to load views when the user log in.
     */
    private void loadViews() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profile.fxml"));
            BorderPane profilePane = loader.load();
            ProfileController profileController = loader.getController();
            controllerMap.put("profile", profileController);

            loader = new FXMLLoader(getClass().getResource("/fxml/actualities.fxml"));
            BorderPane actualitiesPane = loader.load();
            ActualitiesController actualitiesController = loader.getController();
            controllerMap.put("actualities", actualitiesController);

            loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
            BorderPane settingsPane = loader.load();
            SettingsController settingsController = loader.getController();
            controllerMap.put("settings", settingsController);

            myProfile.addObserver(profileController);
            myProfile.addObserver(actualitiesController);
            myProfile.addObserver(settingsController);
            screenController.addScreen("profile", profilePane);
            screenController.addScreen("actualities", actualitiesPane);
            screenController.addScreen("settings", settingsPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function called to load the profile view that the user want to visit.
     */
    public static void loadVisitorView() {
        try {
            FXMLLoader loader = new FXMLLoader(MainView.class.getResource("/fxml/profileVisitor.fxml"));
            BorderPane profileVisitorPane = loader.load();
            ProfileVisitorController profileVisitorController = loader.getController();
            controllerMap.put("profileVisitor", profileVisitorController);

            screenController.addScreen("profileVisitor", profileVisitorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialization of the profile to visit.
     * @param profile Profile to visit.
     */
    public static void initVisitProfile(Profile profile) {
        profilVisitor = profile;

        loadVisitorView();
    }

    /**
     * Initialization of the search list view
     * @param o Profiles list or text to search.
     */
    public static void initSearchResult(Object o) {
        try {
            FXMLLoader loader = new FXMLLoader(MainView.class.getResource("/fxml/search.fxml"));
            BorderPane searchPane = loader.load();
            SearchController searchController = loader.getController();
            if( o instanceof String ) searchController.initResult((String) o);
            else searchController.initResult((HashSet<Long>) o);
            controllerMap.put("search", searchController);

            screenController.addScreen("search", searchPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialization of the user profile and node when he connects.
     * @param profile User profile
     * @param node User profile node
     */
    public void init(Profile profile, Node node) {
        myProfile = profile;
        myNode = node;

        loadViews();
    }

    /**
     * Function to run to launch the software.
     * @param args Additional information.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
