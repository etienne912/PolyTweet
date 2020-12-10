package com.polyTweet.dao.message.data;

import com.polyTweet.model.Profile;

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
