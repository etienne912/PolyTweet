package com.polyTweet.node.adapter;

import com.polyTweet.node.Node;
import com.polyTweet.node.message.Message;
import com.polyTweet.node.message.MessageTypesEnum;
import com.polyTweet.node.message.data.*;
import com.polyTweet.node.socket.Server;
import com.polyTweet.profile.Profile;

import java.io.IOException;
import java.util.List;

public class ServerAdapter {

	private final Node node;
	private final Server server;

	public ServerAdapter(String nodeIp, Node pNode) throws IOException {
		node = pNode;
		server = new Server(nodeIp, this);
		server.open();
	}

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
//				server.close(data.getNeighborInfo());
			}
		}

		return new Message(MessageTypesEnum.ACK, null, null);
	}

	public void close() {
		server.close();
	}
}
