package com.polyTweet.dao.message.data;

public class SearchProfileData extends Data {

	private final long id;

	public SearchProfileData(long id, boolean broadcast) {
		super(broadcast);
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
