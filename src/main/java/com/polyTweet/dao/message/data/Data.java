package com.polyTweet.dao.message.data;

import java.io.Serializable;

public abstract class Data implements Serializable {

	private final boolean broadcast;

	public Data(boolean broadcast) {
		this.broadcast = broadcast;
	}

	public boolean needBroadcast() {
		return broadcast;
	}
}
