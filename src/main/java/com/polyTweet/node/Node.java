package com.polyTweet.node;

import com.polyTweet.node.exceptions.MaxNodeException;
import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private final List<Node> neighbors;
	private final List<ProfileCache> cache;
	private final Profile myProfile;
	private final long id;

	public Node(Profile myProfile) {
		this.neighbors = new ArrayList<>();
		this.cache = new ArrayList<>();
		this.myProfile = myProfile;
		this.id = myProfile.getId();
	}

	public Profile getProfile() {
		return this.myProfile;
	}

	public long getId() {
		return id;
	}

	public void addNeighbor(Node neighbor) throws MaxNodeException {
		if (this.neighbors.size() < 5)
			this.neighbors.add(neighbor);
		else
			throw new MaxNodeException();
	}

	public Profile searchProfile(long id) throws NodeNotFoundException {
		Profile result = this.searchProfile(id, new Itinerary());
		if (result != null)
			return result;
		else
			throw new NodeNotFoundException(id);
	}

	public Profile searchProfile(long id, Itinerary oldItinerary) {
		for (Node node : this.neighbors) {
			Profile tmp = node.getProfile();
			if (tmp.getId() == id)
				return tmp;
		}

		for (Node node : this.neighbors) {
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

}
