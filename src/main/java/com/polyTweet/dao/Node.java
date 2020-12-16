package com.polyTweet.dao;

import com.polyTweet.dao.adapter.ClientAdapter;
import com.polyTweet.dao.adapter.ServerAdapter;
import com.polyTweet.model.Profile;
import com.polyTweet.model.ProfileCache;

import java.net.BindException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * This class makes a gateway that allows the user to access the network
 */
public class Node {

	private static final int MAX_NODE_INFORMATION_CAPACITY = 5;

	private final HashMap<String, ClientAdapter> neighbors;
	private final HashMap<Long, ProfileCache> cache;
	private final HashMap<Long, Integer> traficMonitor;
	private final HashMap<String, Date> messageIdLog;
	private final Profile myProfile;
	private final String myIp;
	private ServerAdapter serverAdapter;

	public Node(Profile myProfile, String nodeIp) throws BindException, UnknownHostException {
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

	/**
	 * Used to just establish a connection between the node and another node
	 *
	 * @param nodeIp The other node's IP
	 */
	public void addNeighborSimple(String nodeIp) {
		if (nodeIp == null) return;
		if (this.isNotFull()) {
			try {
				ClientAdapter neighbor = new ClientAdapter(nodeIp);
				this.neighbors.put(nodeIp, neighbor);
			} catch (ConnectException | UnknownHostException exception) {
				exception.printStackTrace();
			}
		}
	}

	/**
	 * Used to establish a connection between the node and another node and ask it to connect with this node
	 *
	 * @param nodeIp The other node's IP
	 */
	public void addNeighbor(String nodeIp) throws ConnectException, UnknownHostException {
		if (nodeIp != null && !this.neighbors.containsKey(nodeIp) && this.isNotFull()) {
			if (nodeIp.equals(this.myIp)) throw new ConnectException();

			ClientAdapter neighbor = new ClientAdapter(nodeIp);
			neighbor.addMyNode(this.myIp);
			this.neighbors.put(nodeIp, neighbor);
		}
	}

	public Set<String> getNeighbors() {
		return this.neighbors.keySet();
	}

	/**
	 * Used to close a connection between the node and another node and ask it to close its connection too
	 *
	 * @param nodeIp The other node's IP
	 */
	public void removeNeighbor(String nodeIp) {
		if (neighbors.containsKey(nodeIp))
			neighbors.get(nodeIp).close(this.myIp);
		neighbors.remove(nodeIp);
	}

	/**
	 * Used to request a connection with other nodes by broadcasting a message over the network
	 *
	 * @throws ConnectException It's throw if the connection creation is refused
	 */
	public void requestNodeConnection() throws ConnectException, UnknownHostException {
		this.requestNodeConnection(MAX_NODE_INFORMATION_CAPACITY - this.getNbNeighbors());
	}

	/**
	 * Used to request a connection with other nodes by broadcasting a message over the network
	 *
	 * @param nbNodes Number of new neighbours needed
	 * @throws ConnectException It's throw if the connection creation is refused
	 */
	public void requestNodeConnection(int nbNodes) throws ConnectException, UnknownHostException {
		if (0 < nbNodes && nbNodes <= MAX_NODE_INFORMATION_CAPACITY - this.getNbNeighbors())
			this.requestNodeConnection(this.myIp, this.myIp.hashCode() + "requestNodeConnection" + new Date().getTime(), nbNodes);
	}

	/**
	 * Used to send and forward node requests over the network
	 *
	 * @param requesterIp Requester's IP
	 * @param messageId   Message identifier
	 * @param nbNodes     Number of new neighbours needed
	 * @throws ConnectException It's throw if the connection creation is refused
	 */
	public void requestNodeConnection(String requesterIp, String messageId, int nbNodes) throws ConnectException, UnknownHostException {
		// if there is no more desired node or if the node has already processed this message (to fight against the network cycle)
		if (nbNodes <= 0 || this.messageIdLog.containsKey(messageId)) return;
		this.messageIdLog.put(messageId, new Date());

		if (this.isNotFull() && !this.myIp.equals(requesterIp)) {
			this.addNeighbor(requesterIp);
			nbNodes--;
		}

		ArrayList<ClientAdapter> neighbors = new ArrayList<>(this.neighbors.values());

		for (ClientAdapter neighbor : neighbors) {
			neighbor.requestNodeConnection(requesterIp, messageId, nbNodes);
		}
	}

	/**
	 * Used to obtain the list of profiles that the user follows
	 *
	 * @return The list of profiles returned by the network
	 */
	public List<Profile> getProfileFollowedInformation() {
		ArrayList<Profile> profileFollowed = new ArrayList<>();

		this.myProfile.getFollowedProfiles().forEach(id -> profileFollowed.add(this.searchProfile(id)));

		return profileFollowed;
	}

	public int getNbNeighbors() {
		return this.neighbors.size();
	}

	public boolean isNotFull() {
		return this.neighbors.size() < MAX_NODE_INFORMATION_CAPACITY;
	}

	/**
	 * Used to monitor the exchange of data passing through our node
	 * In a future version, this will make it possible to dynamically manage the node's neighbourhood by deleting little-used nodes and to connect to heavily used nodes
	 *
	 * @param id Identifier of the requested profile
	 */
	public void increaseMonitor(long id) {
		this.traficMonitor.compute(id, (k, v) -> (v == null) ? 1 : v + 1);
	}

	public void flushCache() {
		this.cache.clear();
	}

	/**
	 * Used to obtain a profile by requesting it from network nodes with its identifier
	 *
	 * @param id Requested profile's identifier
	 * @return The profile requested
	 */
	public Profile searchProfile(long id) {
		this.increaseMonitor(id);

		// Before sending a request over the network, I create a messageId that is as unique as possible
		return this.searchProfile(id, this.myIp.hashCode() + "searchProfile" + new Date().getTime(), true);
	}

	/**
	 * Used to send and forward search profile requests over the network
	 *
	 * @param id        Requested profile's identifier
	 * @param messageId Message identifier
	 * @param broadcast True if the message should be broadcast to the node's neighbours
	 * @return The profile requested
	 */
	public Profile searchProfile(long id, String messageId, boolean broadcast) {
		if (this.messageIdLog.containsKey(messageId)) return null;
		if (broadcast)
			this.messageIdLog.put(messageId, new Date());

		if (this.myProfile.getId() == id) return myProfile;

		if (!broadcast) return null;

		boolean b = false;
		for (int i = 0; i < 2; i++) {
			ArrayList<ClientAdapter> neighbors = new ArrayList<>(this.neighbors.values());
			for (ClientAdapter neighbor : neighbors) {
				Profile profile = neighbor.searchProfile(id, messageId, b);

				if (profile != null) {
					if (profile instanceof ProfileCache) { // If the profile has been returned from the cache of a node
						// If I already have this profile in cache and mine is more recent
						if (this.cache.containsKey(profile.getId()) && ((ProfileCache) profile).getCachedDate().compareTo(this.cache.get(profile.getId()).getCachedDate()) >= 0) {
							return this.cache.get(profile.getId()); // I return mine cached profile
						} else {
							this.cache.put(profile.getId(), (ProfileCache) profile);
							return profile;
						}
					} else { // If it's not a cached profile, the node adds it to its cache.
						this.cache.put(profile.getId(), new ProfileCache(profile));
						return profile;
					}
				}
			}
			// If the requested profile is not in the neighborhood, the node sends a broadcast request
			b = true;
		}

		// After failed to find the requested profile over the network, the node searches in its cache
		return this.cache.getOrDefault(id, null);
	}

	/**
	 * Used to obtain a list of profiles accessible on the network whose names correspond to the request
	 *
	 * @param match A string to which the profiles must correspond
	 * @return A list with all the profile accessible on the network whose names correspond to the request
	 */
	public List<Profile> searchProfile(String match) {
		return this.searchProfile(match, this.myIp.hashCode() + "searchProfileByName" + new Date().getTime());
	}

	/**
	 * Used to send and forward search profile requests over the network
	 *
	 * @param match     A string to which the profiles must correspond
	 * @param messageId Message identifier
	 * @return A list with all the profile accessible on the network whose names correspond to the request
	 */
	public List<Profile> searchProfile(String match, String messageId) {
		if (this.messageIdLog.containsKey(messageId)) return null;
		this.messageIdLog.put(messageId, new Date());

		ArrayList<Profile> profiles = new ArrayList<>();

		if (this.myProfile.getName().toLowerCase(Locale.ROOT).contains(match.toLowerCase(Locale.ROOT))) // matching tests
			profiles.add(myProfile);

		ArrayList<ClientAdapter> neighbors = new ArrayList<>(this.neighbors.values());
		for (ClientAdapter neighbor : neighbors) { // Broadcast the request to the neighborhood
			List<Profile> result = neighbor.searchProfile(match, messageId);

			if (result != null) {
				result.forEach(profile -> {
					if (profile instanceof ProfileCache) {
						if (this.cache.containsKey(profile.getId()) && ((ProfileCache) profile).getCachedDate().compareTo(this.cache.get(profile.getId()).getCachedDate()) >= 0) {
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

		// After checking on the network, the node searches in its cache for another matching profile
		this.cache.values().forEach(profile -> {
			if (profile.getName().toLowerCase(Locale.ROOT).contains(match.toLowerCase(Locale.ROOT)) && !profiles.contains(profile))
				profiles.add(profile);
		});

		return profiles;
	}

	/**
	 * To close the node properly
	 */
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
