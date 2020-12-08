package com.polyTweet.node.message.data;

public class SearchProfileByNameData extends Data {

	private final String name;

	public SearchProfileByNameData(String name) {
		super(true);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
