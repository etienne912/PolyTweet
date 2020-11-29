package com.polyTweet.profile;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
	private final Date date;
	private final String message;

	public Post(String message) {
		this.date = new Date();
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
}
