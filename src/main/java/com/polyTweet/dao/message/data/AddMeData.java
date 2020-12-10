package com.polyTweet.dao.message.data;

public class AddMeData extends Data {

	private final String nodeIp;

	public AddMeData(String pNodeIp) {
		super(false);
		this.nodeIp = pNodeIp;
	}

	public String getNeighborIp() {
		return nodeIp;
	}
}
