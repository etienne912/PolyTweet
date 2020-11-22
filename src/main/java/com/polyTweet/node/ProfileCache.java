package com.polyTweet.node;

import com.polyTweet.profile.Profile;

import java.util.Date;

public class ProfileCache {
	private Date date;
	private Profile profile;

	public ProfileCache(Profile profile) {
		this.date = new Date();;
		this.profile = profile;
	}

	public Date getDate() {
		return date;
	}

	public Profile getProfile() {
		return profile;
	}

	public void update(Profile profile) {
		this.profile = profile;
		this.date = new Date();
	}
}
