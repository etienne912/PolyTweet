package com.polyTweet.node;

import com.polyTweet.node.adapter.ClientAdapter;
import com.polyTweet.node.adapter.ServerAdapter;
import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;
import com.polyTweet.profile.ProfileCache;

import java.net.BindException;
import java.util.*;

public class Node {

	private static final int MAX_NODE_INFORMATION_CAPACITY = 5;

	private final HashMap<String, ClientAdapter> neighbors;
	private final HashMap<Long, ProfileCache> cache;
	private final HashMap<Long, Integer> traficMonitor;
	private final HashMap<String, Date> messageIdLog;
	private final Profile myProfile;
	private final String myIp;
	private ServerAdapter serverAdapter;

	public Node(Profile myProfile, String nodeIp) throws BindException {
		this.neighbors = new HashMap<>(MAX_NODE_INFORMATION_CAPACITY);
		this.cache = new HashMap<>();
		this.traficMonitor = new HashMap<>();
		this.messageIdLog = new HashMap<>();
		this.myProfile = myProfile;
		this.myIp = nodeIp;
		this.serverAdapter = new ServerAdapter(nodeIp, this);
	}

	public Profile getProfile() {
		return this.myProfile;
	}

	public String getNodeIp() {
		return this.myIp;
	}

	public void addNeighborSimple(String nodeIp) {
		if (nodeIp == null) return;
		if (this.isNotFull()) {
			ClientAdapter neighbor = new ClientAdapter(nodeIp);
			this.neighbors.put(nodeIp, neighbor);
		}
	}

	public void addNeighbor(String nodeInfo) {
		if (nodeInfo != null && !this.neighbors.containsKey(nodeInfo) && this.isNotFull()) {
			ClientAdapter neighbor = new ClientAdapter(nodeInfo);
			this.neighbors.put(nodeInfo, neighbor);
			neighbor.addMyNode(this.myIp);

//			if (this.getNbNeighbors() == 1) {
//				this.requestNodeConnection();
//			}
		}
	}

	public HashMap<Long, ProfileCache> getCache() {
		return cache;
	}

	public void removeNeighbor(String nodeIp) {
		if (neighbors.containsKey(nodeIp))
			neighbors.get(nodeIp).close(this.myIp);
		neighbors.remove(nodeIp);
	}

	public void follow(long id) {
		this.myProfile.follow(id);
	}

	public void unfollow(long id) {
		this.myProfile.unfollow(id);
	}

	public void requestNodeConnection() {
		this.requestNodeConnection(MAX_NODE_INFORMATION_CAPACITY - this.getNbNeighbors());
	}

	public void requestNodeConnection(int nbNodes) {
		if (0 < nbNodes && nbNodes <= MAX_NODE_INFORMATION_CAPACITY - this.getNbNeighbors())
			this.requestNodeConnection(this.myIp, this.myIp.hashCode() + "requestNodeConnection" + new Date().getTime(), nbNodes);
	}

	public void requestNodeConnection(String nodeInfo, String messageId, int nbNodes) {
		if (nbNodes <= 0 || this.messageIdLog.containsKey(messageId)) return;
		this.messageIdLog.put(messageId, new Date());

		if (this.isNotFull()) {
			this.addNeighbor(nodeInfo);
			nbNodes--;
		}

		ArrayList<ClientAdapter> neighbors = new ArrayList<>(this.neighbors.values());

		for (ClientAdapter neighbor : neighbors) {
			neighbor.requestNodeConnection(nodeInfo, messageId, nbNodes);
		}
	}

	public List<Profile> getProfileFollowedInformation() {
		ArrayList<Profile> profileFollowed = new ArrayList<>();

		this.myProfile.getProfileFollowed().forEach(id -> {
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
		Profile result = this.searchProfile(id, this.myIp.hashCode() + "searchProfile" + new Date().getTime(), true);

//		if (result != null)
		return result;
	}

	public void flushCache() {
		this.cache.clear();
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

				if (profile != null) {
					if (profile instanceof ProfileCache) {
						if (this.cache.containsKey(profile.getId()) && ((ProfileCache) profile).getDate().compareTo(this.cache.get(profile.getId()).getDate()) >= 0) {
							return this.cache.get(profile.getId());
						} else {
							this.cache.put(profile.getId(), (ProfileCache) profile);
							return profile;
						}
					} else {
						this.cache.put(profile.getId(), new ProfileCache(profile));
						return profile;
					}
				}
			}
			b = true;
		}

		return this.cache.getOrDefault(id, null);
	}

	public List<Profile> searchProfile(String name) {
		return this.searchProfile(name, this.myIp.hashCode() + "searchProfileByName" + new Date().getTime());
	}

	public List<Profile> searchProfile(String name, String messageId) {
		if (this.messageIdLog.containsKey(messageId)) return null;
		this.messageIdLog.put(messageId, new Date());

		ArrayList<Profile> profiles = new ArrayList<>();

		if (this.myProfile.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
			profiles.add(myProfile);

		for (ClientAdapter neighbor : this.neighbors.values()) {
			List<Profile> result = neighbor.searchProfile(name, messageId);

			if (result != null) {
				result.forEach(profile -> {
					if (profile instanceof ProfileCache) {
						if (this.cache.containsKey(profile.getId()) && ((ProfileCache) profile).getDate().compareTo(this.cache.get(profile.getId()).getDate()) >= 0) {
							profiles.add(this.cache.get(profile.getId()));
						} else {
							this.cache.put(profile.getId(), (ProfileCache) profile);
							profiles.add(profile);
						}
					} else {
						this.cache.put(profile.getId(), new ProfileCache(profile));
						profiles.add(profile);
					}
				});
			}
		}

		this.cache.values().forEach(profile -> {
			if (profile.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)) && !profiles.contains(profile))
				profiles.add(profile);
		});

		return profiles;
	}

	public void close() {
		ArrayList<String> neighbors = new ArrayList<>(this.neighbors.keySet());

		for (String neighborIp : neighbors) {
			this.removeNeighbor(neighborIp);
		}

		if (serverAdapter != null)
			serverAdapter.close();
		serverAdapter = null;
	}

	@Override
	public String toString() {
		return "Node{" +
				"ProfileId=" + myProfile.getId() +
				", NodeIp=" + myIp +
				", neighbors=" + neighbors.keySet() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		return myIp.equals(node.myIp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(myIp);
	}

}
