package com.polyTweet.dao.message.data;

/**
 * This class represents the data sent by a socket between two nodes to request connections from other nodes on the network.
 */
public class RequestConnectionData extends Data {

	private final String nodeIp;
	private final int nbNodes;

	/**
	 * The Constructor
	 *
	 * @param nodeIp  Requester's IP
	 * @param nbNodes Number of new neighbours needed
	 */
	public RequestConnectionData(String nodeIp, int nbNodes) {
		super(true);
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
