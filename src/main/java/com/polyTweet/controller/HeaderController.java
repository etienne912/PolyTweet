package com.polyTweet.controller;

import com.jfoenix.controls.JFXTextField;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Header view controller.
 */
public class HeaderController implements Initializable {

	@FXML
	public Button profileButton, actualitiesButton, settingsButton;
	public JFXTextField searchField;

	/**
	 * Header Constructor.
	 */
	public HeaderController() {}

	/**
	 * Initialization of the view.
	 * @param location location of the view
	 * @param resources information for the initialisation
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {}

	/**
	 * Listener called when the user click on the button to load the profile view.
	 * @param e Event
	 */
	@FXML
	public void profileClick(ActionEvent e) {
		MainView.switchScene("profile");
	}

	/**
	 * Listener called when the user click on the button to load the actualities view.
	 * @param e Event
	 */
	@FXML
	private void actualitiesClick(ActionEvent e) {
		MainView.switchScene("actualities");
	}

	/**
	 * Listener called when the user click on the button to load the settings view.
	 * @param e Event
	 */
	@FXML
	private void settingsClick(ActionEvent e) {
		MainView.switchScene("settings");
	}

	/**
	 * Listener called when the user click on the button to log out.
	 */
	@FXML
	private void logoutClick(ActionEvent e) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Log out ?");
		alert.setHeaderText("Are you sure you want log out ?");

		Optional<ButtonType> option = alert.showAndWait();

		if (option.get() == ButtonType.OK) {
			MainView.disconnection();
		} else {
			e.consume();
		}
	}

	/**
	 * Listener called when the user click on the button to search a profile in the network.
	 * @param e Event
	 */
	@FXML
	private void searchClick(ActionEvent e) {
		if(!this.searchField.getText().equals("")){
			MainView.initSearchResult(this.searchField.getText());
			this.searchField.setText("");
			MainView.switchScene("search");
		}
	}

}