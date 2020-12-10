package com.polyTweet.controller;

import com.polyTweet.profile.Post;
import com.polyTweet.profile.Profile;
import com.polyTweet.view.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileVisitorController implements Initializable {

	@FXML
	public Label firstName, lastName, status;
	public VBox profilePosts;
	public Button followButton;

	private static Profile profile;

	public ProfileVisitorController() {
		profile = MainView.getProfileVisitor();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initView();
	}

	public void initView() {
		this.firstName.setText(profile.getFirstName());
		this.lastName.setText(profile.getLastName());
		this.status.setText(profile.getStatus());

		if (MainView.getProfile().isFollowing(profile.getId())) this.followButton.setText("Unfollow");
		else this.followButton.setText("Follow");

		List<Post> posts = profile.getPosts();

		this.profilePosts.getChildren().clear();

		for (int i = posts.size() - 1; i >= 0; i--) {
			this.profilePosts.getChildren().add(new Label(posts.get(i).getDate().toString() + " - " + posts.get(i).getMessage()));
		}
	}

	public void followClick() {
		if (!MainView.getProfile().isFollowing(profile.getId())) {
			MainView.getProfile().follow(profile.getId());
		} else {
			MainView.getProfile().unfollow(profile.getId());
		}
	}

	public void update() {
		this.initView();
	}

}