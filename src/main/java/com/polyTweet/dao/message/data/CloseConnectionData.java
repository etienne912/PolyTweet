package com.polyTweet.dao.message.data;

public class CloseConnectionData extends Data {

	private final String nodeIp;

	public CloseConnectionData(String pNodeIp) {
		super(false);
		this.nodeIp = pNodeIp;
	}

	public String getNeighborIp() {
		return nodeIp;
	}
}
