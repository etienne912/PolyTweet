package com.polyTweet.controller;

import com.polyTweet.Observer;
import com.polyTweet.model.Post;
import com.polyTweet.model.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Abstract class of Profile view controller.
 */
public abstract class ProfileController implements Observer {

	@FXML
	public Label firstName, lastName, status;
	public VBox profilePosts;

	private static Profile profile;


	/**
	 * Profile Constructor.
	 */
	public ProfileController(Profile p) {
		profile = p;
	}

	/**
	 * Function to set Profile variable.
	 * @param myProfile Profile to set
	 */
	public void setVars(Profile myProfile) {
		this.profile = myProfile;
	}

	/**
	 * Function to initialise the user profile.
	 */
	public void initView() {
		this.firstName.setText(profile.getFirstName());
		this.lastName.setText(profile.getLastName());
		this.status.setText(profile.getStatus());

		ArrayList<Post> sortedPosts = new ArrayList<>(profile.getPosts());
		sortedPosts.sort((p1, p2) -> p2.getWrittenDate().compareTo(p1.getWrittenDate()));

		if (this.profilePosts != null) this.profilePosts.getChildren().clear();
		sortedPosts.forEach((post) -> {
			BorderPane postPane = new BorderPane();

			Label labelName = new Label(profile.getFirstName() + " " + profile.getLastName());
			labelName.getStyleClass().add("profileNameLabel");


			String date = post.getWrittenDate().toString().replaceAll("(.{3}) (.{3}) ([0-9]{2}) ([0-9]{2}:[0-9]{2}):[0-9]{2} CET ([0-9]{4})", "$3 $2 $5, $4" );

			Label labelPost = new Label(date + " - " + post.getMessage());
			labelPost.getStyleClass().add("postLabel");

			postPane.setTop(labelName);
			postPane.setCenter(labelPost);

			profilePosts.getChildren().add(postPane);
		});
	}

}
