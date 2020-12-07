package com.polyTweet.node.message.data;

import com.polyTweet.node.NodeInfo;

public class CloseConnectionData extends Data {

	private final NodeInfo nodeInfo;

	public CloseConnectionData(NodeInfo pNodeInfo) {
		super(false);
		this.nodeInfo = pNodeInfo;
	}

	public NodeInfo getNeighborInfo() {
		return nodeInfo;
	}
}
