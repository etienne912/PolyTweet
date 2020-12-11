package com.polyTweet.model;

import java.util.Date;

/**
 * Represents a cached profile with the date of caching
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
