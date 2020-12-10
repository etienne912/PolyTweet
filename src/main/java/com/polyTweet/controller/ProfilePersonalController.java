package com.polyTweet.controller;

import com.polyTweet.Observable;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePersonalController extends ProfileController implements Initializable {

	@FXML
	public Label followedNb;
	public VBox profilePosts;

	private static Profile profile;

	public ProfilePersonalController() {
		super();
		profile = MainView.getProfile();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initView();
	}

	public void initView() {
		this.followedNb.setText(Integer.toString(profile.getFollowedProfiles().size()));
		super.initView();
	}

	@Override
	public void update(Observable observable) {
		this.initView();
	}
}