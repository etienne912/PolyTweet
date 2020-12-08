package com.polyTweet.node;

import com.polyTweet.node.adapter.ClientAdapter;
import com.polyTweet.node.adapter.ServerAdapter;
import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;

import java.util.*;

public class Node {

	private static final int MAX_NODE_INFORMATION_CAPACITY = 5;

	private final HashMap<NodeInfo, ClientAdapter> neighbors;
	private final HashMap<String, ProfileCache> cache;
	private final ArrayList<Long> follow;
	private final HashMap<Long, Integer> traficMonitor;
	private final HashMap<String, Date> messageIdLog;
	private final Profile myProfile;
	private final NodeInfo myNodeInfo;

	public Node(Profile myProfile, NodeInfo nodeInfo) {
		this.neighbors = new HashMap<>(MAX_NODE_INFORMATION_CAPACITY);
		this.cache = new HashMap<>(MAX_NODE_INFORMATION_CAPACITY);
		this.follow = new ArrayList<>(MAX_NODE_INFORMATION_CAPACITY);
		this.traficMonitor = new HashMap<>();
		this.messageIdLog = new HashMap<>();
		this.myProfile = myProfile;
		this.myNodeInfo = nodeInfo;
		new ServerAdapter(nodeInfo, this);
	}

	public Profile getProfile() {
		return this.myProfile;
	}

	public NodeInfo getNodeInfo() {
		return this.myNodeInfo;
	}

	public void addNeighborSimple(NodeInfo nodeInfo) {
		if (nodeInfo == null) return;
		if (this.isNotFull()) {
			ClientAdapter neighbor = new ClientAdapter(nodeInfo);
			this.neighbors.put(nodeInfo, neighbor);
		}
	}

	public void addNeighbor(NodeInfo nodeInfo) {
		if (nodeInfo != null && !this.neighbors.containsKey(nodeInfo) && this.isNotFull()) {
			ClientAdapter neighbor = new ClientAdapter(nodeInfo);
			this.neighbors.put(nodeInfo, neighbor);
			neighbor.addMyNode(this.myNodeInfo);

//			if (this.getNbNeighbors() == 1) {
//				this.requestNodeConnection();
//			}
		}
	}

	public void removeNeighbor(NodeInfo nodeInfo) {
		neighbors.remove(nodeInfo);
	}

	public void addFollow(long id) {
		this.follow.add(id);
	}

	public void requestNodeConnection() {
		this.requestNodeConnection(MAX_NODE_INFORMATION_CAPACITY - this.getNbNeighbors());
	}

	public void requestNodeConnection(int nbNodes) {
		if (0 < nbNodes && nbNodes <= MAX_NODE_INFORMATION_CAPACITY - this.getNbNeighbors())
			this.requestNodeConnection(this.myNodeInfo, this.myNodeInfo.hashCode() + "" + new Date(), nbNodes);
	}

	public void requestNodeConnection(NodeInfo nodeInfo, String messageId, int nbNodes) {
		if (nbNodes <= 0 || this.messageIdLog.containsKey(messageId)) return;
		this.messageIdLog.put(messageId, new Date());

		if (this.getNbNeighbors() < MAX_NODE_INFORMATION_CAPACITY) {
			this.addNeighbor(nodeInfo);
			nbNodes--;
		}

		for (ClientAdapter neighbor : this.neighbors.values()) {
			neighbor.requestNodeConnection(nodeInfo, messageId, nbNodes);
		}
	}

	public List<Profile> getProfileFollowedInformation() {
		ArrayList<Profile> profileFollowed = new ArrayList<>();

		this.follow.forEach(id -> {
			try {
				profileFollowed.add(this.searchProfile(id));
			} catch (NodeNotFoundException e) {
				e.printStackTrace();
			}
		});

		return profileFollowed;
	}

	public int getNbNeighbors() {
		return this.neighbors.size();
	}

	public boolean isNotFull() {
		return this.neighbors.size() < MAX_NODE_INFORMATION_CAPACITY;
	}

	public void increaseMonitor(long id) {
		this.traficMonitor.compute(id, (k, v) -> (v == null) ? 1 : v + 1);
	}

	public Profile searchProfile(long id) throws NodeNotFoundException {
		this.increaseMonitor(id);
		Profile result = this.searchProfile(id, this.myNodeInfo.hashCode() + "" + new Date(), true);

		if (result != null)
			return result;
		else
			throw new NodeNotFoundException(id);
	}

	public Profile searchProfile(long id, String messageId, boolean broadcast) {
		if (this.messageIdLog.containsKey(messageId)) return null;
		if (broadcast)
			this.messageIdLog.put(messageId, new Date());

		if (this.myProfile.getId() == id) return myProfile;

		if (!broadcast) return null;

		boolean b = false;
		for (int i = 0; i < 2; i++) {
			for (ClientAdapter neighbor : this.neighbors.values()) {
				Profile profile = neighbor.searchProfile(id, messageId, b);

				if (profile != null)
					return profile;
			}
			b = true;
		}

		return null;
	}

	@Override
	public String toString() {
		return "Node{" +
				"ProfileId=" + myProfile.getId() +
				", NodeInfo=" + myNodeInfo +
				", neighbors=" + neighbors.keySet() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		return myNodeInfo.equals(node.myNodeInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(myNodeInfo);
	}

}
