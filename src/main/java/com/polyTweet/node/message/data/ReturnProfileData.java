package com.polyTweet.node.message.data;

import com.polyTweet.profile.Profile;

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
