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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.BindException;
import java.net.ConnectException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Register view Controller.
 */
public class RegisterController implements Initializable {

	@FXML
	public TextField firstName, lastName, networkIpField, localIpField;
	public Button registerButton;
	public Label errorLabel;

	private MainView view;

	/**
	 * Register Constructor.
	 */
	public void setVars(MainView view) {
		this.view = view;
	}

	/**
	 * Initialization of the view.
	 *
	 * @param location  location of the view
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
	 *
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
		} catch (UnknownHostException exception) {
			this.displayError("Error: wrong ip address! Please try with an IP address");
			return;
		} catch (BindException bindException) {
			this.displayError("Error: Connection refused! Please try with another IP address");
			return;
		}

		if (!this.networkIpField.getText().equals("")) {
			try {
				node.addNeighbor(this.networkIpField.getText());
				new Thread(() -> {
					try {
						node.requestNodeConnection();
					} catch (ConnectException | UnknownHostException exception) {
						exception.printStackTrace();
					}
				}).start();
			} catch (UnknownHostException exception) {
				this.displayError("Error: wrong ip address! Please try with an IP address");
				node.close();
				return;
			} catch (ConnectException connectException) {
				this.displayError("Error: Connection refused! Please try with another IP address");
				node.close();
				return;
			}
		}

		this.firstName.setText("");
		this.lastName.setText("");
		this.errorLabel.setVisible(false);

		view.init(profile, node);
		view.switchScene("actualities");
	}

	private void displayError(String errorMessage) {
		this.errorLabel.setText(errorMessage);
		this.errorLabel.setVisible(true);
		this.errorLabel.setStyle("-fx-padding: 10 30 10 30;-fx-font-size: 15px;");
	}

	/**
	 * Listener called when the user click on the return button to come back to the login view.
	 *
	 * @param e Event
	 */
	@FXML
	public void returnLogin(ActionEvent e) {
		view.switchScene("login");
	}

}