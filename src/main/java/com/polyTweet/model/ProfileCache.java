package com.polyTweet.model;

import java.util.Date;

/**
 * Represent a profile saved in cache
 */
public class ProfileCache extends Profile {
	private final Date cachedDate;

	public ProfileCache(Profile profile) {
		super(profile);
		this.cachedDate = new Date();
	}

	public Date getCachedDate() {
		return cachedDate;
	}

}
