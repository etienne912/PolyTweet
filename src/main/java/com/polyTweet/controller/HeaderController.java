package com.polyTweet.controller;

import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {

	private final Profile profile;
	private final Node node;
	private List<Profile> profiles;

	@FXML
	public Button profileButton, actualitiesButton, settingsButton;
	public VBox listProfile;

	public HeaderController() {
		this.node = MainView.getNode();
		this.profile = MainView.getProfile();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//TODO
	}

	@FXML
	public void profileClick(ActionEvent e) {
		MainView.switchScene("profile");
	}

	@FXML
	private void actualitiesClick(ActionEvent e) {
		MainView.switchScene("actualities");
	}

	@FXML
	private void settingsClick(ActionEvent e) {
		MainView.switchScene("settings");
	}

	@FXML
	private void logoutClick() {
		MainView.closeWindow();
	}

	@FXML
	private void searchProfile(ActionEvent e) {
		TextField search = (TextField) e.getTarget();
		profiles = this.node.searchProfile(search.getText());

		this.listProfile.getChildren().clear();

		this.profiles.forEach(p -> {
			Button profileButton = new Button();
			profileButton.setOnAction(this::clickProfile);
			profileButton.setText(p.getFirstName() + " " + p.getLastName());

			this.listProfile.getChildren().add(profileButton);
		});
	}

	@FXML
	private void clickProfile(ActionEvent e) {
		Button button = (Button) e.getTarget();
		String[] entireName = button.getText().split(" ");

		this.listProfile.getChildren().clear();

		for (Profile p : this.profiles) {
			if (entireName[0].equals(p.getFirstName()) && entireName[1].equals(p.getLastName())) {
				if (p.equals(profile)) {
					MainView.switchScene("profile");
				} else {
					MainView.initVisitProfile(p);
					MainView.switchScene("profileVisitor");
				}
				break;
			}
		}
	}

}