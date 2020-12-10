package com.polyTweet.controller;

import com.polyTweet.Observer;
import com.polyTweet.model.Post;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public abstract class ProfileController implements Observer {

    @FXML
    public Label firstName, lastName, status;
    public VBox profilePosts;

    private static Profile profile;

    public ProfileController() {
        profile = MainView.getProfile();
    }

    public void setVars(Profile myProfile) {
        this.profile = myProfile;
    }

    public void initView() {
        this.firstName.setText(profile.getFirstName());
        this.lastName.setText(profile.getLastName());
        this.status.setText(profile.getStatus());

        ArrayList<Post> sortedPosts = new ArrayList<>(profile.getPosts());
        sortedPosts.sort((p1, p2) -> p2.getWrittenDate().compareTo(p1.getWrittenDate()));

        this.profilePosts.getChildren().clear();
        sortedPosts.forEach((post) -> this.profilePosts.getChildren().add(new Label(post.getWrittenDate().toString() + " - " + post.getMessage())));
    }

}
