package com.polyTweet.controller;

import com.polyTweet.profile.Profile;
import com.polyTweet.view.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    public Label firstName, lastName, status;
    public VBox profilePosts;

    private static Profile profile;

    public ProfileController() {
        profile = MainView.getProfile();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.firstName.setText(profile.getFirstName());
        this.lastName.setText(profile.getLastName());
        this.status.setText(profile.getStatus());

        profile.getPosts().forEach(p -> this.profilePosts.getChildren().add(new Label(p.getMessage())));
    }

    public void update() {
        this.firstName.setText(profile.getFirstName());
        this.lastName.setText(profile.getLastName());
        this.status.setText(profile.getStatus());
    }

}