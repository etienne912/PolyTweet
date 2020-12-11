package com.polyTweet.controller;

import com.polyTweet.Observable;
import com.polyTweet.Observer;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Settings view controller.
 */
public class SettingsController implements Initializable, Observer {

	private Profile profile;

	@FXML
	public TextField firstNameField, lastNameField, statusField;

	/**
	 * Settings Constructor.
	 */
	public SettingsController() {
		this.profile = MainView.getProfile();
	}

	/**
	 * Initialization of the view.
	 * @param location location of the view
	 * @param resources information for the initialisation
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initSettings();
	}

	private void initSettings() {
		this.firstNameField.setText(profile.getFirstName());
		this.lastNameField.setText(profile.getLastName());
		this.statusField.setText(profile.getStatus());
	}

	/**
	 * Listener called when the user click on the button to update his personal information.
	 * @param e Event
	 */
	@FXML
	public void validateClick(ActionEvent e) {
		if (!this.firstNameField.getText().equals(this.profile.getFirstName()))
			this.profile.setFirstName(this.firstNameField.getText());
		if (!this.lastNameField.getText().equals(this.profile.getLastName()))
			this.profile.setLastName(this.lastNameField.getText());
		if (!this.statusField.getText().equals(this.profile.getStatus()))
			this.profile.setStatus(this.statusField.getText());
	}

	/**
	 * Function to update the view.
	 * @param observable Observable element
	 */
	@Override
	public void update(Observable observable) {
		this.initSettings();
	}
}