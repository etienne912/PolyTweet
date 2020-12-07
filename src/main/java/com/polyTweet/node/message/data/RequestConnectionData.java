package com.polyTweet.node.message.data;

import com.polyTweet.node.NodeInfo;

public class RequestConnectionData extends Data {

	private final NodeInfo nodeInfo;
	private final int nbNodes;

	public RequestConnectionData(NodeInfo nodeInfo, int nbNodes, boolean broadcast) {
		super(broadcast);
		this.nodeInfo = nodeInfo;
		this.nbNodes = nbNodes;
	}

	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}

	public int getNbNodes() {
		return nbNodes;
	}
}
