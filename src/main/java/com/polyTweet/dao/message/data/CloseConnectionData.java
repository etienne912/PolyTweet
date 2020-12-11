package com.polyTweet.dao.message.data;

/**
 * This class represents the data sent by a socket between two nodes to close the connection
 */
public class CloseConnectionData extends Data {

	private final String nodeIp;

	/**
	 * This type of message requires only the IP address of the requesting node
	 *
	 * @param pNodeIp The IP address
	 */
	public CloseConnectionData(String pNodeIp) {
		super(false);
		this.nodeIp = pNodeIp;
	}

	public String getNeighborIp() {
		return nodeIp;
	}
}
