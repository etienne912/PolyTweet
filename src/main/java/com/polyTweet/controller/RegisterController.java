package com.polyTweet.controller;

import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
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

/**
 * Register view Controller.
 */
public class RegisterController implements Initializable {

	@FXML
	public TextField firstName, lastName, networkIpField, localIpField;
	public Button registerButton;

	private MainView view;

	/**
	 * Register Constructor.
	 */
	public void setVars(MainView view) {
		this.view = view;
	}

	/**
	 * Initialization of the view.
	 * @param location location of the view
	 * @param resources information for the initialisation
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BooleanBinding booleanBind = Bindings.or(firstName.textProperty().isEmpty(),
				lastName.textProperty().isEmpty())
				.or(localIpField.textProperty().isEmpty());

		this.registerButton.disableProperty().bind(booleanBind);
	}

	/**
	 * Listener called when the user click on the button to register.
	 * @param e Event
	 */
	@FXML
	public void registerClick(ActionEvent e) {

		String firstName = this.firstName.getText();
		String lastName = this.lastName.getText();


		Profile profile = new Profile(firstName, lastName);
		Node node;
		try {
			node = new Node(profile, this.localIpField.getText());
		} catch (BindException bindException) {
			System.err.println("Address already in use");
			return;
		}

		if (!this.networkIpField.getText().equals("")) {
			node.addNeighbor(this.networkIpField.getText());
			new Thread(node::requestNodeConnection).start();
		}

		this.firstName.setText("");
		this.lastName.setText("");

		view.init(profile, node);
		view.switchScene("actualities");
	}

	/**
	 * Listener called when the user click on the return button to come back to the login view.
	 * @param e Event
	 */
	@FXML
	public void returnLogin(ActionEvent e) {
		view.switchScene("login");
	}

}