package com.polyTweet.controller;

import com.jfoenix.controls.JFXButton;
import com.polyTweet.Observable;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * User Profile view Controller.
 */
public class ProfilePersonalController extends ProfileController implements Initializable {

	@FXML
	public JFXButton followedNb;
	public VBox profilePosts;

	/**
	 * User profile Constructor.
	 */
	public ProfilePersonalController() {
		super();
		profile = MainView.getProfile();
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

	/**
	 * Function to initialise the user profile.
	 */
	public void initView() {
		this.followedNb.setText(Integer.toString(profile.getFollowedProfiles().size()));
		super.initView();
	}

	/**
	 * Function to update the view.
	 * @param observable Observable element
	 */
	@Override
	public void update(Observable observable) {
		if (observable instanceof Profile)
			this.initView();
	}

	/**
	 * Listener called when the user click on the number of followed profiles to load a list of them.
	 * @param e Event
	 */
	@FXML
	public void followClick(ActionEvent e) {
		MainView.initSearchResult(MainView.getProfileVisitor().getFollowedProfiles());
		MainView.switchScene("search");
	}
}