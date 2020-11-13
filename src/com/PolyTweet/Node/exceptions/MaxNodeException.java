package com.PolyTweet.Node.exceptions;

public class MaxNodeException extends Exception {
	public MaxNodeException() {
		super("You can't add more 5 neighbors");
	}
}
