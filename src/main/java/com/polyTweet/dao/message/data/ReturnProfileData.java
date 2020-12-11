package com.polyTweet.dao.message.data;

import com.polyTweet.model.Profile;
import com.polyTweet.model.ProfileCache;

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
		if (pProfile == null) {
			profile = null;
		} else {
			profile = pProfile.getClass() == ProfileCache.class ? new ProfileCache(pProfile) : new Profile(pProfile);
		}
	}

	public Profile getProfile() {
		return profile;
	}
}
