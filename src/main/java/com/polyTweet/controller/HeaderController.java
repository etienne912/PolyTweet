package com.polyTweet.controller;

import com.jfoenix.controls.JFXTextField;
import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {

	private static Profile profile;
	private static Node node;

	@FXML
	public Button profileButton, actualitiesButton, settingsButton;
	public JFXTextField searchField;

	public HeaderController() {
		node = MainView.getNode();
		profile = MainView.getProfile();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

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
		MainView.disconnection();
	}

	@FXML
	private void searchClick(ActionEvent e) {
		if(!this.searchField.getText().equals("")){
			MainView.initSearchResult(this.searchField.getText());
			this.searchField.setText("");
			MainView.switchScene("search");
		}
	}

}