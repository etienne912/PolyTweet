package com.polyTweet.controller;

import com.polyTweet.profile.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

	private final Profile profile;

	@FXML
	public TextField firstNameField, lastNameField, statusField;

	public SettingsController() {
		this.profile = MainView.getProfile();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.firstNameField.setText(profile.getFirstName());
		this.lastNameField.setText(profile.getLastName());
		this.statusField.setText(profile.getStatus());
	}

	@FXML
	public void validateClick(ActionEvent e) {
		if (!this.firstNameField.getText().equals(this.profile.getFirstName()))
			this.profile.setFirstName(this.firstNameField.getText());
		if (!this.lastNameField.getText().equals(this.profile.getLastName()))
			this.profile.setLastName(this.lastNameField.getText());
		if (!this.statusField.getText().equals(this.profile.getStatus()))
			this.profile.setStatus(this.statusField.getText());
	}
}