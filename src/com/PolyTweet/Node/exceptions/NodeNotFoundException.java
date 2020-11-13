package com.PolyTweet.Node.exceptions;

public class NodeNotFoundException extends Exception {
	public NodeNotFoundException(long nodeId) {
		super("Node not found : " + nodeId);
	}
}
