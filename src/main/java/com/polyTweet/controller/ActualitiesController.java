package com.polyTweet.controller;

import com.polyTweet.profile.Profile;
import com.polyTweet.view.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ActualitiesController implements Initializable {

    private Profile profile;

    @FXML
    public TextField postText;
    public Button postButton;
    public VBox actualitiesPosts;

    public ActualitiesController() {
        this.profile = MainView.getProfile();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.profile.getPosts().forEach(p -> {
            actualitiesPosts.getChildren().add(new Label(p.getMessage()));
        });
    }

}