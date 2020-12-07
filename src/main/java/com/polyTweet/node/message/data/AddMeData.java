package com.polyTweet.node.message.data;

import com.polyTweet.node.NodeInfo;

public class AddMeData extends Data {

	private final NodeInfo nodeInfo;

	public AddMeData(NodeInfo pNodeInfo) {
		super(false);
		this.nodeInfo = pNodeInfo;
	}

	public NodeInfo getNeighborInfo() {
		return nodeInfo;
	}
}
