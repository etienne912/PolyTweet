package com.polyTweet.dao.message.data;

import com.polyTweet.model.Profile;

/**
 * This class represents the data sent by a socket between two nodes to return a profile
 */
public class ReturnProfileData extends Data {

	private final Profile profile;

	/**
	 * The constructor
	 *
	 * @param pProfile The profile to be returned
	 */
	public ReturnProfileData(Profile pProfile) {
		super(false);
		profile = pProfile;
	}

	public Profile getProfile() {
		return profile;
	}
}
