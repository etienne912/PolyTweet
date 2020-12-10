package com.polyTweet.controller;

import com.polyTweet.node.Node;
import com.polyTweet.profile.Profile;
import com.polyTweet.profile.ProfileView;
import com.polyTweet.view.MainView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.BindException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

	@FXML
	public TextField firstName, lastName, networkIpField, localIpField;
	public Button registerButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BooleanBinding booleanBind = Bindings.or(firstName.textProperty().isEmpty(),
				lastName.textProperty().isEmpty())
				.or(localIpField.textProperty().isEmpty());

		this.registerButton.disableProperty().bind(booleanBind);
	}

	@FXML
	public void registerClick(ActionEvent e) {

		String firstName = this.firstName.getText();
		String lastName = this.lastName.getText();


		Profile profile = new ProfileView(firstName, lastName);
		Node node;
		try {
			node = new Node(profile, this.networkIpField.getText());
		} catch (BindException bindException) {
			System.err.println("Address already in use");
			return;
		}

		if (!this.networkIpField.getText().equals(""))
			node.addNeighbor(this.networkIpField.getText());

		this.firstName.setText("");
		this.lastName.setText("");

		MainView.init(profile, node);
		MainView.switchScene("actualities");
	}

	@FXML
	public void returnLogin() {
		MainView.switchScene("login");
	}

}