package com.polyTweet.node;

import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;

import java.util.*;

public class Node {
	private final HashMap<Long, Node> neighbors;
	private final HashMap<Long, ProfileCache> cache;
	private final ArrayList<Long> follow;
	private final HashMap<Long, Integer> traficMonitor;
	private final Profile myProfile;
	private final long id;
	private static long count = 0;
	private static final int MAX_NODE_INFORMATION_CAPACITY = 5;

	public Node(Profile myProfile) {
		this.neighbors = new HashMap<>(MAX_NODE_INFORMATION_CAPACITY);
		this.cache = new HashMap<>(MAX_NODE_INFORMATION_CAPACITY);
		this.follow = new ArrayList<>(MAX_NODE_INFORMATION_CAPACITY);
		this.traficMonitor = new HashMap<>();
		this.myProfile = myProfile;
		this.id = count++;
	}

	public Node(Profile profile, Node enterNode) {
		this(profile);
		this.askNewNodeContactInformation(enterNode);
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
			this.neighbors.put(neighbor.id, neighbor);

//		this.cache.put(neighbor.id, new ProfileCache(neighbor.getProfile()));

		if (neighbor.isNotFull())
			neighbor.neighbors.put(this.id, this);

//		neighbor.cache.put(this.id, new ProfileCache(this.myProfile));
	}

	public void addNeighbors(List<Node> neighbors) {
		neighbors.forEach(this::addNeighbor);
	}

	public void addFollow(long id) {
		this.follow.add(id);
	}

	public void askNewNodeContactInformation(Node enterNode) {
		this.addNeighbor(enterNode);
		this.askNewNodeContactInformation();
	}

	public void askNewNodeContactInformation() {
		this.askNewNodeContactInformation(MAX_NODE_INFORMATION_CAPACITY - this.getNbNeighbors());
	}

	public void askNewNodeContactInformation(int nbNodes) {
		if (0 < nbNodes && nbNodes <= MAX_NODE_INFORMATION_CAPACITY - this.getNbNeighbors())
			this.addNeighbors(this.askNewNodeContactInformation(nbNodes, new Itinerary(Collections.singletonList(this.id))));
	}

	public List<Node> askNewNodeContactInformation(int nbNodes, Itinerary oldItinerary) {
		if (nbNodes <= 0) return Collections.emptyList();

		ArrayList<Node> nodesContactInformation = new ArrayList<>(nbNodes);

		for (Node node : this.neighbors.values()) {
			if (node.isNotFull() && nbNodes > 0 && !oldItinerary.hasAlreadyPassed(node.getId())) {
				nodesContactInformation.add(node);
				nbNodes--;
				if (nbNodes == 0) return nodesContactInformation;
			}
		}

		for (Node node : this.neighbors.values()) {
			if (!oldItinerary.hasAlreadyPassed(node.getId())) {
				try {
					Itinerary clone = (Itinerary) oldItinerary.clone();
					List<Node> nodes = node.askNewNodeContactInformation(nbNodes, clone.addNodeId(this.id));

					if (nodes != null) {
						if (nodes.size() < nbNodes) {
							nodesContactInformation.addAll(nodes);
							nbNodes -= nodes.size();
						} else {
							nodesContactInformation.addAll(nodes.subList(0, nbNodes));
							return nodesContactInformation;
						}
					}
				} catch (CloneNotSupportedException e) {
					return null;
				}
			}
		}

		return nodesContactInformation;
	}

	public Node getNodeInformation(long id, Itinerary oldItinerary) {
		if (this.id == id) return this;

		if (this.neighbors.containsKey(id))
			return this.neighbors.get(id);

		for (Node node : this.neighbors.values()) {
			if (!oldItinerary.hasAlreadyPassed(node.getId())) {
				try {
					Itinerary clone = (Itinerary) oldItinerary.clone();
					Node tmp = node.getNodeInformation(id, clone.addNodeId(this.id));

					if (tmp != null)
						return tmp;
				} catch (CloneNotSupportedException e) {
					return null;
				}
			}
		}

		return null;
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
		if (this.isNotFull() && !this.neighbors.containsKey(id))
			this.addNeighbor(this.getNodeInformation(id, new Itinerary()));
		Profile result = this.searchProfile(id, new Itinerary());

		if (result != null)
			return result;
		else
			throw new NodeNotFoundException(id);
	}

	public Profile searchProfile(long id, Itinerary oldItinerary) {
		if (this.neighbors.containsKey(id)) {
			this.cache.put(id, new ProfileCache(this.neighbors.get(id).getProfile()));
			return this.neighbors.get(id).getProfile();
		} else if (this.cache.containsKey(id)) {
			return this.cache.get(id).getProfile();
		}

		for (Node node : this.neighbors.values()) {
			if (!oldItinerary.hasAlreadyPassed(node.id)) {
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
