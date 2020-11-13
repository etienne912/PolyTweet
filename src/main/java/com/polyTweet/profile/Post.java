package com.polyTweet.profile;

import java.util.Date;

public class Post {
	private final Date date;
	private final String message;

	public Post(Date date, String message) {
		this.date = date;
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
}
