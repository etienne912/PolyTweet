package com.polyTweet.controller;

import com.polyTweet.node.Node;
import com.polyTweet.profile.Post;
import com.polyTweet.profile.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class ActualitiesController implements Initializable {

    private Profile profile;
    private Node node;

    @FXML
    public TextField postText;
    public Button postButton;
    public VBox actualitiesPosts;

    public ActualitiesController() {
        this.profile = MainView.getProfile();
        this.node = MainView.getNode();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initPost();
    }

    private void initPost() {
        Map<Date, Post> sortedPosts = new TreeMap<>();

        List<Profile> profileFollowed = this.node.getProfileFollowedInformation();

        profileFollowed.forEach(pf -> {
            List<Post> posts = pf.getPosts();
            posts.forEach(p -> sortedPosts.put(p.getDate(), p));
        });

        profile.getPosts().forEach(p -> sortedPosts.put(p.getDate(), p));

        if( this.actualitiesPosts.getChildren().size() != 0 ) this.actualitiesPosts.getChildren().clear();
        sortedPosts.forEach( (k, v) -> this.actualitiesPosts.getChildren().add(new Label(v.getFirstname() + " " + v.getLastname() + " : " + k.toString() + " - " + v.getMessage())));
    }

    @FXML
    public void postClick(ActionEvent e) {
        String post = this.postText.getText();
        if(!post.equals("")) {
            this.postText.setText("");
            profile.writePost(post);
        }
    }

    public void update() {
        this.initPost();
    }

}