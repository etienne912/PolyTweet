package com.polyTweet.controller;

import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
import com.polyTweet.utils.serialization.Deserialization;
import com.polyTweet.view.MainView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.BindException;
import java.net.ConnectException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Log in view controller.
 */
public class LoginController implements Initializable {

	MainView view;

	@FXML
	public Label filePath, errorLabel;
	public TextField networkIpField, localIpField;
	public Button loginButton;
	private final BooleanProperty fileSelected = new SimpleBooleanProperty(false);

	private final FileChooser chooser;
	private File file;

	/**
	 * Log in Constructor.
	 */
	public LoginController() {
		this.chooser = new FileChooser();
		this.chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized profile", "*.ser"));
	}

	/**
	 * Function to set MainView.
	 *
	 * @param mainView MainView class
	 */
	public void setVars(MainView mainView) {
		this.view = mainView;
	}

	/**
	 * Initialization of the view.
	 *
	 * @param location  location of the view
	 * @param resources information for the initialisation
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BooleanBinding fileSelectedCompleted = fileSelected.isNotEqualTo(new SimpleBooleanProperty(true));

		BooleanBinding booleanBind = Bindings.or(fileSelectedCompleted,
				localIpField.textProperty().isEmpty());
		this.loginButton.disableProperty().bind(booleanBind);
	}

	/**
	 * Listener called when the user click on the button to import a serialized profile.
	 *
	 * @param e Event
	 */
	@FXML
	public void importClick(ActionEvent e) {
		this.file = chooser.showOpenDialog(this.view.getPrimaryStage());
		if (this.file != null) {
			this.filePath.setTextFill(Color.BLACK);
			this.filePath.setText(this.file.getName());
			fileSelected.setValue(true);
		}
	}

	/**
	 * Listener called when the user click on the button to connect to his profile.
	 *
	 * @param e Event
	 */
	@FXML
	public void connexionClick(ActionEvent e) {
		Profile profile = (Profile) Deserialization.deserialize(this.file.getPath());

		if (profile == null) {
			this.filePath.setText("Profile incompatible");
			this.filePath.setTextFill(Color.RED);
			this.file = null;
			fileSelected.setValue(false);
			return;
		}

		Node node;
		try {
			node = new Node(profile, this.localIpField.getText());
		} catch (UnknownHostException exception) {
			this.displayError("Error: wrong ip address ! Please try with an IP address");
			return;
		} catch (BindException bindException) {
			this.displayError("Error: IP address already in use ! Please try with another IP address");
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
				this.displayError("Error: wrong ip address ! Please try with an IP address");
				node.close();
				return;
			} catch (ConnectException connectException) {
				this.displayError("Error: Connection refused ! Please try with another IP address");
				node.close();
				return;
			}
		}

		this.file = null;
		this.filePath.setText("No profile selected");
		fileSelected.setValue(false);
		this.errorLabel.setVisible(false);

		this.view.init(profile, node);
		this.view.switchScene("actualities");
	}

	private void displayError(String errorMessage) {
		this.errorLabel.setText(errorMessage);
		this.errorLabel.setVisible(true);
		this.errorLabel.setStyle("-fx-padding: 10 30 10 30;-fx-font-size: 15px;");
	}

	/**
	 * Listener called when the user click on the button to create a new profile.
	 *
	 * @param e Event
	 */
	@FXML
	public void registerClick(ActionEvent e) {
		this.view.switchScene("register");
	}

}