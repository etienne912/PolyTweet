package com.polyTweet.controller;

import com.polyTweet.Observer;
import com.polyTweet.model.Post;
import com.polyTweet.model.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Abstract class of Profile view controller.
 */
public abstract class ProfileController implements Observer {

	@FXML
	public Label firstName, lastName, status;
	public VBox profilePosts;

	protected Profile profile;


	/**
	 * Profile Constructor.
	 */
	public ProfileController() {
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

			Locale locale = new Locale("fr", "FR");
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
			String date = dateFormat.format(post.getWrittenDate());

			Label labelPost = new Label(date + " - " + post.getMessage());
			labelPost.getStyleClass().add("postLabel");

			postPane.setTop(labelName);
			postPane.setCenter(labelPost);

			profilePosts.getChildren().add(postPane);
		});
	}

}
