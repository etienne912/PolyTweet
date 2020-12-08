package com.polyTweet.controller;

import com.polyTweet.profile.Post;
import com.polyTweet.profile.Profile;
import com.polyTweet.view.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ProfileController implements Initializable {

    @FXML
    public Label firstName, lastName, status, followedNb;
    public VBox profilePosts;

    private static Profile profile;

    public ProfileController() { profile = MainView.getProfile(); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       this.initView();
    }

    public void initView() {
        this.firstName.setText(profile.getFirstName());
        this.lastName.setText(profile.getLastName());
        this.status.setText(profile.getStatus());
        this.followedNb.setText(Integer.toString(profile.getProfileFollowed().size()));

        Map<Date, Post> sortedPosts = new TreeMap<>();

        profile.getPosts().forEach(p -> sortedPosts.put(p.getDate(), p));

        if( this.profilePosts.getChildren().size() != 0 ) this.profilePosts.getChildren().clear();
        sortedPosts.forEach( (k, v) -> this.profilePosts.getChildren().add(new Label(v.getLastname() + " : " + k.toString() + " - " + v.getMessage())));
    }

    public void update() {
        this.initView();
    }

}