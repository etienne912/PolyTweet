package com.polyTweet.controller;

import com.polyTweet.node.Node;
import com.polyTweet.node.NodeInfo;
import com.polyTweet.profile.Profile;
import com.polyTweet.serialization.Deserialization;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Label filePath;

    private final FileChooser chooser;
    private File file;

    public LoginController() {
        this.chooser = new FileChooser();
        this.chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Sérialisé", "*.ser"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void importClick(ActionEvent e) {
        this.file = chooser.showOpenDialog(MainView.getPrimaryStage());
        String path = this.file.getPath();
        this.filePath.setText(path);
    }

    @FXML
    public void connexionClick(ActionEvent e) {
        Profile profile = (Profile) Deserialization.deserialize(this.file.getPath());
        System.out.println(profile);
        Node node = new Node(profile, new NodeInfo("127.0.0.1", 5000));

        this.file = null;
        this.filePath.setText("");

        MainView.init(profile, node);
        MainView.switchScene("actualities");
    }

    @FXML
    public void registerClick(ActionEvent e) {
        MainView.switchScene("register");
    }

}