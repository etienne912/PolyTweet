package com.polyTweet.dao.exceptions;

public class MaxNodeException extends Exception {
	public MaxNodeException() {
		super("You can't add more 5 neighbors");
	}
}
