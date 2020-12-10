package com.polyTweet.profile;

import com.polyTweet.view.MainView;

public class ProfileView extends Profile {

	public ProfileView(String firstName, String lastName) {
		super(firstName, lastName);
	}

	public void setFirstName(String firstName) {
		super.setFirstName(firstName);
		MainView.update();
	}

	public void setLastName(String lastName) {
		super.setLastName(lastName);
		MainView.update();
	}

	public void setStatus(String status) {
		super.setStatus(status);
		MainView.update();
	}

	public void writePost(String message) {
		super.writePost(message);
		MainView.update();
	}

	public void unfollow(Long id) {
		super.unfollow(id);
		MainView.updateProfileVisitor();
	}

	public void follow(Long id) {
		super.follow(id);
		MainView.updateProfileVisitor();
	}

}
