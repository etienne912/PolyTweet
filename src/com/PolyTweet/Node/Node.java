package com.PolyTweet.Node;

import com.PolyTweet.Profile.Profile;

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

	public Profile searchProfile(long id) {
		return this.searchProfile(id, new Itinerary());
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

	public void addNeighbor(Node neighbor) {
		this.neighbors.add(neighbor);
	}


}
