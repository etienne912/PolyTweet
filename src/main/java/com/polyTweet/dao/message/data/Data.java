package com.polyTweet.dao.message.data;

import java.io.Serializable;

/**
 * This class represents the data sent by a socket between two nodes
 */
public abstract class Data implements Serializable {

	private final boolean broadcast;

	public Data(boolean broadcast) {
		this.broadcast = broadcast;
	}

	public boolean needBroadcast() {
		return broadcast;
	}
}
