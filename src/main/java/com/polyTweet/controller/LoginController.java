package com.polyTweet.controller;

import com.polyTweet.node.Node;
import com.polyTweet.profile.Profile;
import com.polyTweet.serialization.Deserialization;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

	@FXML
	public Label filePath;
	public TextField networkIpField, localIpField;
	public Button loginButton;
	private final BooleanProperty fileSelected = new SimpleBooleanProperty(false);

	private final FileChooser chooser;
	private File file;

	public LoginController() {
		this.chooser = new FileChooser();
		this.chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Sérialisé", "*.ser"));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BooleanBinding fileSelectedCompleted = fileSelected.isNotEqualTo(new SimpleBooleanProperty(true));

		BooleanBinding booleanBind = Bindings.or(fileSelectedCompleted,
				localIpField.textProperty().isEmpty());
		this.loginButton.disableProperty().bind(booleanBind);
	}

	@FXML
	public void importClick(ActionEvent e) {
		this.file = chooser.showOpenDialog(MainView.getPrimaryStage());
		if (this.file != null) {
			String path = this.file.getPath();
			this.filePath.setText(path);
			fileSelected.setValue(true);
		}
	}

	@FXML
	public void connexionClick(ActionEvent e) {
		Profile profile = (Profile) Deserialization.deserialize(this.file.getPath());

		Node node = null;
		try {
			node = new Node(profile, this.localIpField.getText());
		} catch (BindException bindException) {
			System.err.println("Address already in use");
			return;
		} catch (IOException ignored) {
			return;
		}

		if (!this.networkIpField.getText().equals(""))
			node.addNeighbor(this.networkIpField.getText());

		this.file = null;
		this.filePath.setText("Aucun Profile Sélectionné");
		fileSelected.setValue(false);

		MainView.init(profile, node);
		MainView.switchScene("actualities");
	}

	@FXML
	public void registerClick(ActionEvent e) {
		MainView.switchScene("register");
	}

}