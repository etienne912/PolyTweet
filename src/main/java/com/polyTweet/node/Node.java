package com.polyTweet.node;

import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;

import java.util.*;

public class Node {
	private final HashMap<Long, Node> neighbors;
	private final HashMap<Long, ProfileCache> cache;
	private final Profile myProfile;
	private final long id;
	private static long count = 0;

	public Node(Profile myProfile) {
		this.neighbors = new HashMap<>();
		this.cache = new HashMap<>();
		this.myProfile = myProfile;
		this.id = count++;
	}

	public Profile getProfile() {
		return this.myProfile;
	}

	public long getId() {
		return id;
	}

	public void addNeighbor(Node neighbor) {
		if (neighbor == null) return;
		if (this.isNotFull())
			this.neighbors.put(neighbor.getId(), neighbor);
		if (neighbor.isNotFull())
			neighbor.neighbors.put(this.id, this);
	}

	public void searchEnterPoint(Node node) {
		this.addNeighbor(node.searchEnterPoint(new Itinerary()));
	}

	public Node searchEnterPoint(Itinerary oldItinerary) {
		if (this.isNotFull()) {
			return this;
		}

		for (Node node : this.neighbors.values()) {
			if (node.isNotFull()) {
				return node;
			}
		}

		for (Node node : this.neighbors.values()) {
			if (!oldItinerary.hasAlreadyPassed(node.getId())) {
				try {
					Itinerary clone = (Itinerary) oldItinerary.clone();
					Node enterNode = node.searchEnterPoint(clone.addNodeId(this.id));

					if (enterNode != null)
						return enterNode;
				} catch (CloneNotSupportedException e) {
					return null;
				}
			}
		}

		return null;
	}

	public Node getNodeinformation(long id, Itinerary oldItinerary) {
		if (this.id == id) return this;

		if (this.neighbors.containsKey(id))
			return this.neighbors.get(id);

		for (Node node : this.neighbors.values()) {
			if (!oldItinerary.hasAlreadyPassed(node.getId())) {
				try {
					Itinerary clone = (Itinerary) oldItinerary.clone();
					Node tmp = node.getNodeinformation(id, clone.addNodeId(this.id));

					if (tmp != null)
						return tmp;
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

	public boolean isNotFull() {
		return this.neighbors.size() < 5;
	}

	public Profile searchProfile(long id) throws NodeNotFoundException {
		if (this.isNotFull() && !this.neighbors.containsKey(id))
			this.addNeighbor(this.getNodeinformation(id, new Itinerary()));
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
