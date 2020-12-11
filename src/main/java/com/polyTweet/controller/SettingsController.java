package com.polyTweet.controller;

import com.jfoenix.controls.JFXButton;
import com.polyTweet.Observable;
import com.polyTweet.Observer;
import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.ConnectException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Settings view controller.
 */
public class SettingsController implements Initializable, Observer {

	public Label errorLabel;
	private Profile profile;
	private Node node;

	@FXML
	public TextField firstNameField, lastNameField, statusField, ipField;
	public HBox hboxNeighbors;
	public JFXButton addButton, removeButton;

	/**
	 * Settings Constructor.
	 */
	public SettingsController() {
		this.node = MainView.getNode();
		this.profile = MainView.getProfile();
	}

	/**
	 * Initialization of the view.
	 *
	 * @param location  location of the view
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

		if (node.getNbNeighbors() > 0) {
			this.removeButton.setDisable(false);
			if (node.getNbNeighbors() < 5) {
				this.addButton.setDisable(false);
				this.hboxNeighbors.getChildren().clear();
				node.getNeighbors().forEach(neighbor -> {

					Label label = new Label(neighbor);
					label.getStyleClass().add("neighborLabel");

					this.hboxNeighbors.getChildren().add(label);
				});
			} else {
				this.addButton.setDisable(true);
			}
		} else {
			this.removeButton.setDisable(true);
			this.hboxNeighbors.getChildren().clear();

			Label label = new Label("You don't have neighbors");
			label.getStyleClass().add("neighborLabel");

			this.hboxNeighbors.getChildren().add(label);
		}
	}

	/**
	 * Listener called when the user click on the button to update his personal information.
	 *
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

	@FXML
	public void addClick(ActionEvent e) {
		String ip = this.ipField.getText();
		if (!ip.equals("")) {
			this.ipField.setText("");
			try {
				node.addNeighbor(ip);
				this.initSettings();
			} catch (ConnectException connectException) {
				this.displayError("Error: Connection refused! Please try with another IP address");
			} catch (UnknownHostException exception) {
				this.displayError("Error: wrong ip address! Please try with an IP address");
			}
		}
		this.errorLabel.setVisible(false);
	}

	private void displayError(String errorMessage) {
		this.errorLabel.setText(errorMessage);
		this.errorLabel.setVisible(true);
		this.errorLabel.setStyle("-fx-padding: 10 30 10 30;-fx-font-size: 15px;");
	}

	@FXML
	public void removeClick(ActionEvent e) {
		String ip = this.ipField.getText();
		if (!ip.equals("")) {
			this.ipField.setText("");
			node.removeNeighbor(ip);
			this.initSettings();
		}
	}

	@FXML
	public void refreshClick(ActionEvent e) {
		this.initSettings();
	}

	/**
	 * Function to update the view.
	 *
	 * @param observable Observable element
	 */
	@Override
	public void update(Observable observable) {
		this.initSettings();
	}
}