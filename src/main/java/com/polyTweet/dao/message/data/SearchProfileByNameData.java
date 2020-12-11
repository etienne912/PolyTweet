package com.polyTweet.dao.message.data;

/**
 * This class represents the data sent by a socket between two nodes to search a profile by name
 */
public class SearchProfileByNameData extends Data {

	private final String name;

	/**
	 * The constructor
	 *
	 * @param name A string to which the profiles must correspond
	 */
	public SearchProfileByNameData(String name) {
		super(true);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
