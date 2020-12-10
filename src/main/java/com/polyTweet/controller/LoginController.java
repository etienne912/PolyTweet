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
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

	MainView view;

	@FXML
	public Label filePath;
	public TextField networkIpField, localIpField;
	public Button loginButton;
	private final BooleanProperty fileSelected = new SimpleBooleanProperty(false);

	private final FileChooser chooser;
	private File file;

	public LoginController() {
		this.chooser = new FileChooser();
		this.chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized profile", "*.ser"));
	}

	public void setVars(MainView mainView) {
		this.view = mainView;
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
		this.file = chooser.showOpenDialog(this.view.getPrimaryStage());
		if (this.file != null) {
			String path = this.file.getPath();
			this.filePath.setTextFill(Color.BLACK);
			this.filePath.setText(path);
			fileSelected.setValue(true);
		}
	}

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
		} catch (BindException bindException) {
			System.err.println("Address already in use");
			return;
		}

		if (!this.networkIpField.getText().equals("")) {
			node.addNeighbor(this.networkIpField.getText());
			new Thread(node::requestNodeConnection).start();
		}

		this.file = null;
		this.filePath.setText("Aucun Profile Sélectionné");
		fileSelected.setValue(false);

		this.view.init(profile, node);
		this.view.switchScene("actualities");
	}

	@FXML
	public void registerClick(ActionEvent e) {
		this.view.switchScene("register");
	}

}