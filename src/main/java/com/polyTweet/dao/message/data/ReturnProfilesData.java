package com.polyTweet.dao.message.data;

import com.polyTweet.model.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data sent by a socket between two nodes to return a list of profiles
 */
public class ReturnProfilesData extends Data {

	private final List<Profile> profiles;

	/**
	 * The constructor
	 *
	 * @param pProfiles The list of profile to be returned
	 */
	public ReturnProfilesData(List<Profile> pProfiles) {
		super(false);
		profiles = new ArrayList<>(pProfiles == null ? 0 : pProfiles.size());
		if (pProfiles != null)
			pProfiles.forEach(profile -> profiles.add(new Profile(profile)));
	}

	public List<Profile> getProfiles() {
		return profiles;
	}
}
