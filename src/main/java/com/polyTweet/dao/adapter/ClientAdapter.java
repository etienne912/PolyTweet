package com.polyTweet.dao.adapter;

import com.polyTweet.dao.message.Message;
import com.polyTweet.dao.message.MessageTypesEnum;
import com.polyTweet.dao.message.data.*;
import com.polyTweet.dao.socket.Client;
import com.polyTweet.model.Profile;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * This class is an adapter to the message to send to a specific node. All the messages to send are proceed in this class
 * All their methods have an handler in the {@link ServerAdapter}
 */
public class ClientAdapter {

	private final Client client;

	/**
	 * The constructor
	 *
	 * @param nodeIp The IP address of the node to be contacted
	 */
	public ClientAdapter(String nodeIp) throws ConnectException, UnknownHostException {
		client = new Client(nodeIp);
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
		ReturnProfileData result = (ReturnProfileData) client.send(new Message(MessageTypesEnum.SEARCH_PROFILE, messageId, new SearchProfileData(id, broadcast))).getData();

		return result.getProfile();
	}

	/**
	 * Used to send and forward search profile requests over the network
	 *
	 * @param name      A string to which the profiles must correspond
	 * @param messageId Message identifier
	 * @return A list with all the profile accessible on the network whose names correspond to the request
	 */
	public List<Profile> searchProfile(String name, String messageId) {
		ReturnProfilesData result = (ReturnProfilesData) client.send(new Message(MessageTypesEnum.SEARCH_PROFILE_BY_NAME, messageId, new SearchProfileByNameData(name))).getData();

		return result.getProfiles();
	}

	/**
	 * Used to establish a connection between the node and another node and ask it to connect with this node
	 *
	 * @param nodeInfo My IP address
	 */
	public void addMyNode(String nodeInfo) {
		client.send(new Message(MessageTypesEnum.ADD_ME, null, new AddMeData(nodeInfo)));
	}

	/**
	 * Used to send and forward node requests over the network
	 *
	 * @param requesterIp Requester's IP
	 * @param messageId   Message identifier
	 * @param nbNodes     Number of new neighbours needed
	 */
	public void requestNodeConnection(String requesterIp, String messageId, int nbNodes) {
		client.send(new Message(MessageTypesEnum.REQUEST_CONNECTION, messageId, new RequestConnectionData(requesterIp, nbNodes)));
	}

	/**
	 * Used to close the connection properly
	 *
	 * @param myIp My IP address
	 */
	public void close(String myIp) {
		this.client.close(myIp);
	}
}
