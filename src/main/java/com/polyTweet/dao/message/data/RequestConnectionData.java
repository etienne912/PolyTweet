package com.polyTweet.dao.message.data;

public class RequestConnectionData extends Data {

	private final String nodeIp;
	private final int nbNodes;

	public RequestConnectionData(String nodeIp, int nbNodes, boolean broadcast) {
		super(broadcast);
		this.nodeIp = nodeIp;
		this.nbNodes = nbNodes;
	}

	public String getNodeIp() {
		return nodeIp;
	}

	public int getNbNodes() {
		return nbNodes;
	}
}
