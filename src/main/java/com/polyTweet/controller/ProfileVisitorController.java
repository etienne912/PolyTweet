package com.polyTweet.controller;

import com.polyTweet.Observable;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Visited Profile view Controller.
 */
public class ProfileVisitorController extends ProfileController implements Initializable {

	@FXML
	public Label firstName, lastName, status;
	public VBox profilePosts;
	public Button followButton;

	private static Profile profile;

	/**
	 * Visited profile Constructor.
	 */
	public ProfileVisitorController() {
		super(MainView.getProfileVisitor());
		profile = MainView.getProfileVisitor();
	}

	/**
	 * Initialization of the view.
	 * @param location location of the view
	 * @param resources information for the initialisation
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initView();
	}

	public void initView() {
		if (MainView.getProfile().isFollowing(profile.getId())) this.followButton.setText("Unfollow");
		else this.followButton.setText("Follow");
		super.initView();
	}

	/**
	 * Listener called when the user click on the button to follow / unfollow the profile.
	 * @param e Event
	 */
	public void followClick() {
		if (!MainView.getProfile().isFollowing(profile.getId())) {
			MainView.getProfile().follow(profile.getId());
		} else {
			MainView.getProfile().unfollow(profile.getId());
		}
	}

	/**
	 * Function to update the view.
	 * @param observable Observable element
	 */
	@Override
	public void update(Observable observable) {
		this.initView();
	}
}