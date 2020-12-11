package com.polyTweet.dao.adapter;

import com.polyTweet.dao.Node;
import com.polyTweet.dao.message.Message;
import com.polyTweet.dao.message.MessageTypesEnum;
import com.polyTweet.dao.message.data.*;
import com.polyTweet.dao.socket.Server;
import com.polyTweet.model.Profile;

import java.net.BindException;
import java.util.List;

/**
 * This class is an adapter to the message receive by the server. All the messages received are proceed in this class
 */
public class ServerAdapter {

	private final Node node;
	private final Server server;

	/**
	 * The constructor
	 *
	 * @param nodeIp The node's IP
	 * @param pNode  The node
	 * @throws BindException Throw if the address nodeIp is already in use
	 */
	public ServerAdapter(String nodeIp, Node pNode) throws BindException {
		node = pNode;
		server = new Server(nodeIp, this);
		server.open();
	}

	/**
	 * Allows you to adjust the messages received by the server. All types of messages processed here have been sent using {@link ClientAdapter}'s send method.
	 *
	 * @param message The message received
	 * @return The response of the message
	 */
	public Message adapt(Message message) {
		MessageTypesEnum messageType = message.getType();

		switch (messageType) {
			case ADD_ME -> {
				AddMeData data = (AddMeData) message.getData();

				node.addNeighborSimple(data.getNeighborIp());
			}
			case SEARCH_PROFILE -> {
				SearchProfileData data = (SearchProfileData) message.getData();
				Profile result = node.searchProfile(data.getId(), message.getMessageId(), data.needBroadcast());

				return new Message(MessageTypesEnum.ACK, null, new ReturnProfileData(result));
			}
			case SEARCH_PROFILE_BY_NAME -> {
				SearchProfileByNameData data = (SearchProfileByNameData) message.getData();
				List<Profile> result = node.searchProfile(data.getName(), message.getMessageId());

				return new Message(MessageTypesEnum.ACK, null, new ReturnProfilesData(result));
			}
			case REQUEST_CONNECTION -> {
				RequestConnectionData data = (RequestConnectionData) message.getData();

				node.requestNodeConnection(data.getNodeIp(), message.getMessageId(), data.getNbNodes());
			}
			case CLOSE_CONNECTION -> {
				CloseConnectionData data = (CloseConnectionData) message.getData();
				node.removeNeighbor(data.getNeighborIp());
			}
		}

		return new Message(MessageTypesEnum.ACK, null, null);
	}

	/**
	 * Used to close the server properly
	 */
	public void close() {
		server.close();
	}
}
