package com.polyTweet.controller;

import com.polyTweet.Observer;
import com.polyTweet.model.Post;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public abstract class ProfileController implements Observer {

	@FXML
	public Label firstName, lastName, status;
	public VBox profilePosts;

	private static Profile profile;

	public ProfileController(Profile p) {
		profile = p;
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

		if (this.profilePosts != null) this.profilePosts.getChildren().clear();
		sortedPosts.forEach((post) -> {
			BorderPane postPane = new BorderPane();

			Profile profileVisit = profile;

			if (post.getWriterId() != profile.getId()) {
				profileVisit = MainView.getNode().searchProfile(post.getWriterId());
			}

			if (profileVisit != null) {

				Label labelName = new Label(profileVisit.getFirstName() + " " + profileVisit.getLastName());
				labelName.getStyleClass().add("profileNameLabel");


				Label labelPost = new Label(post.getWrittenDate().toString() + " - " + post.getMessage());
				labelPost.getStyleClass().add("postLabel");

				postPane.setTop(labelName);
				postPane.setCenter(labelPost);

				profilePosts.getChildren().add(postPane);

			}
		});
	}

}
