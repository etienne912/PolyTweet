package com.polyTweet.dao.message.data;

import com.polyTweet.model.Profile;

public class ReturnProfileData extends Data {

	private final Profile profile;

	public ReturnProfileData(Profile pProfile) {
		super(false);
		profile = pProfile;
	}

	public Profile getProfile() {
		return profile;
	}
}
