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

public class MainView extends Application {

	private static HashMap<String, Object> controllerMap;
	private static ScreenController screenController;
	private static Profile myProfile, profilVisitor;
	private static Node myNode;
	private static Stage window;

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
			VBox registerPane = loader.load();
			RegisterController registerController = loader.getController();
			registerController.setVars(this);
			controllerMap.put("register", registerController);


			Scene scene = new Scene(loginPane);

			screenController = new ScreenController(scene);

			screenController.addScreen("login", loginPane);
			screenController.addScreen("register", registerPane);

			final ObservableList<String> stylesheets = scene.getStylesheets();
			stylesheets.addAll(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
					JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
					getClass().getResource("/css/application.css").toExternalForm(),
					getClass().getResource("/css/composants.css").toExternalForm());

			primaryStage.getIcons().add(new Image("/img/polytweet.png"));
			window.setTitle("PolyTweet");
			window.setScene(scene);
			window.show();

			window.setOnCloseRequest(event -> disconnection());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Profile getProfile() {
		return myProfile;
	}

	public static Profile getProfileVisitor() {
		return profilVisitor;
	}

	public static Node getNode() {
		return myNode;
	}

	public static void switchScene(String name) {
		screenController.activate(name);
	}

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

	public static Stage getPrimaryStage() {
		return window;
	}

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

	public static void initVisitProfile(Profile profile) {
		profilVisitor = profile;

		loadVisitorView();
	}

	public void init(Profile profile, Node node) {
		myProfile = profile;
		myNode = node;

		loadViews();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
