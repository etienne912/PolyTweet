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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Settings view controller.
 */
public class SettingsController implements Initializable, Observer {

	private Profile profile;
	private Node node;

	@FXML
	public TextField firstNameField, lastNameField, statusField, ipField;
	public HBox hboxNeighbors;
	public JFXButton joinButton, leaveButton;

	/**
	 * Settings Constructor.
	 */
	public SettingsController() {
		this.node = MainView.getNode();
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

		if( node.getNbNeighbors() > 0 ) {
			this.leaveButton.setDisable(false);
			if(node.getNbNeighbors() < 5) {
				this.joinButton.setDisable(false);
				this.hboxNeighbors.getChildren().clear();
				node.getNeighbors().forEach(neighbor -> {

					Label label = new Label(neighbor);
					label.getStyleClass().add("neighborLabel");

					this.hboxNeighbors.getChildren().add(label);
				});
			} else {
				this.joinButton.setDisable(true);
			}
		} else {
			this.leaveButton.setDisable(true);
			this.hboxNeighbors.getChildren().clear();

			Label label = new Label("You don't have neighbors");
			label.getStyleClass().add("neighborLabel");

			this.hboxNeighbors.getChildren().add(label);
		}
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

	@FXML
	public void joinClick(ActionEvent e) {
		String ip = this.ipField.getText();
		if(!ip.equals("")){
			this.ipField.setText("");
			node.addNeighbor(ip);
			this.initSettings();
		}
	}

	@FXML
	public void leaveClick(ActionEvent e) {
		String ip = this.ipField.getText();
		if(!ip.equals("")){
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
	 * @param observable Observable element
	 */
	@Override
	public void update(Observable observable) {
		this.initSettings();
	}
}