package com.polyTweet.dao.message;

/**
 * This enumerator stores all possible types of messages
 */
public enum MessageTypesEnum {
	ADD_ME, // A node tells another node to create a connection to it
	SEARCH_PROFILE,
	SEARCH_PROFILE_BY_NAME,
	REQUEST_CONNECTION,
	CLOSE_CONNECTION,
	ACK,
}
