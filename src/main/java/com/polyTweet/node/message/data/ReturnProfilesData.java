package com.polyTweet.node.message.data;

import com.polyTweet.profile.Profile;

import java.util.List;

public class ReturnProfilesData extends Data {

	private final List<Profile> profiles;

	public ReturnProfilesData(List<Profile> pProfiles) {
		super(false);
		profiles = pProfiles;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}
}
