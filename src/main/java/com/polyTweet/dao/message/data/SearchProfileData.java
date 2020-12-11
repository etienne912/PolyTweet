package com.polyTweet.dao.message.data;

/**
 * This class represents the data sent by a socket between two nodes to search a profile by identifier
 */
public class SearchProfileData extends Data {

	private final long id;

	/**
	 * The constructor
	 *
	 * @param id        Requested profile's identifier
	 * @param broadcast True if the message should be broadcast to the node's neighbours
	 */
	public SearchProfileData(long id, boolean broadcast) {
		super(broadcast);
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
