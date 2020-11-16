package com.polyTweet.node;

import com.polyTweet.node.exceptions.MaxNodeException;
import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;

import java.util.*;

public class Node {
	private final HashMap<Long, Node> neighbors;
	private final List<ProfileCache> cache;
	private final Profile myProfile;
	private final long id;
	private static long count = 0;

	public Node(Profile myProfile) {
		this.neighbors = new HashMap<>();
		this.cache = new ArrayList<>();
		this.myProfile = myProfile;
		this.id = count++;
	}


	public Profile getProfile() {
		return this.myProfile;
	}

	public long getId() {
		return id;
	}

	public void addNeighbor(Node neighbor) throws MaxNodeException {
		if (this.neighbors.size() < 5)
			this.neighbors.put(neighbor.getId(), neighbor);
		else
			throw new MaxNodeException();
	}

	public void searchEnterPoint(Node node) {
		Node enterNode = node.searchEnterPoint(this, new Itinerary());
		if (enterNode != null)
			this.neighbors.put(enterNode.getId(), enterNode);
	}

	public Node searchEnterPoint(Node newNode, Itinerary oldItinerary) {
		if (this.neighbors.size() < 5) {
			try {
				this.addNeighbor(newNode);
			} catch (MaxNodeException ignored) {
			}
			return this;
		}

		for (Node node : this.neighbors.values()) {
			if (node.getNbNeighbors() < 5) {
				try {
					node.addNeighbor(newNode);
				} catch (MaxNodeException ignored) {
				}
				return node;
			}
		}

		for (Node node : this.neighbors.values()) {
			if (!oldItinerary.hasAlreadyPassed(node.getId())) {
				try {
					Itinerary clone = (Itinerary) oldItinerary.clone();
					Node enterNode = node.searchEnterPoint(newNode, clone.addNodeId(this.id));

					if (enterNode != null)
						return enterNode;
				} catch (CloneNotSupportedException e) {
					return null;
				}
			}
		}

		return null;
	}

	public int getNbNeighbors() {
		return this.neighbors.size();
	}

	public Profile searchProfile(long id) throws NodeNotFoundException {
		Profile result = this.searchProfile(id, new Itinerary());

		if (result != null)
			return result;
		else
			throw new NodeNotFoundException(id);
	}

	public Profile searchProfile(long id, Itinerary oldItinerary) {
		for (Node node : this.neighbors.values()) {
			Profile tmp = node.getProfile();
			if (tmp.getId() == id)
				return tmp;
		}

		for (Node node : this.neighbors.values()) {
			if (!oldItinerary.hasAlreadyPassed(node.getId())) {
				try {
					Itinerary clone = (Itinerary) oldItinerary.clone();
					Profile profile = node.searchProfile(id, clone.addNodeId(this.id));

					if (profile != null)
						return profile;
				} catch (CloneNotSupportedException e) {
					return null;
				}
			}
		}

		return null;
	}

	@Override
	public String toString() {
		return "Node{" +
				"id=" + id +
				", neighbors=" + neighbors.keySet() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		return id == node.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
