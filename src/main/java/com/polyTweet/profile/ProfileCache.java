package com.polyTweet.profile;

import java.util.Date;

public class ProfileCache extends Profile {
	private final Date date;

	public ProfileCache(Profile profile) {
		super(profile);
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}

}
