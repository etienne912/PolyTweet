package com.polyTweet.node.adapter;

import com.polyTweet.node.Node;
import com.polyTweet.node.NodeInfo;
import com.polyTweet.node.message.Message;
import com.polyTweet.node.message.MessageTypesEnum;
import com.polyTweet.node.message.data.*;
import com.polyTweet.node.socket.Server;
import com.polyTweet.profile.Profile;

public class ServerAdapter {

	private final Node node;
	private final Server server;

	public ServerAdapter(NodeInfo nodeInfo, Node pNode) {
		node = pNode;
		server = new Server(nodeInfo, this);
		server.open();
	}

	public Message adapt(Message message) {
		MessageTypesEnum messageType = message.getType();

		switch (messageType) {
			case ADD_ME -> {
				AddMeData data = (AddMeData) message.getData();

				node.addNeighborSimple(data.getNeighborInfo());
			}
			case SEARCH_PROFILE -> {
				SearchProfileData data = (SearchProfileData) message.getData();
				Profile result = node.searchProfile(data.getId(), message.getMessageId(), data.needBroadcast());

				return new Message(MessageTypesEnum.ACK, null, new ReturnProfileData(result));
			}
			case REQUEST_CONNECTION -> {
				RequestConnectionData data = (RequestConnectionData) message.getData();

				node.requestNodeConnection(data.getNodeInfo(), message.getMessageId(), data.getNbNodes());
			}
			case CLOSE_CONNECTION -> {
				CloseConnectionData data = (CloseConnectionData) message.getData();
				node.removeNeighbor(data.getNeighborInfo());
				server.close();
			}
		}

		return new Message(MessageTypesEnum.ACK, null, null);
	}
}