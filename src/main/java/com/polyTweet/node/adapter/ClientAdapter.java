package com.polyTweet.node.adapter;

import com.polyTweet.node.NodeInfo;
import com.polyTweet.node.message.Message;
import com.polyTweet.node.message.MessageTypesEnum;
import com.polyTweet.node.message.data.AddMeData;
import com.polyTweet.node.message.data.RequestConnectionData;
import com.polyTweet.node.message.data.ReturnProfileData;
import com.polyTweet.node.message.data.SearchProfileData;
import com.polyTweet.node.socket.Client;
import com.polyTweet.profile.Profile;

public class ClientAdapter {

	private final Client client;

	public ClientAdapter(NodeInfo nodeInfo) {
		client = new Client(nodeInfo);
	}

	public Profile searchProfile(long id, String messageId, boolean broadcast) {
		ReturnProfileData result = (ReturnProfileData) client.send(new Message(MessageTypesEnum.SEARCH_PROFILE, messageId, new SearchProfileData(id, broadcast))).getData();

		return result.getProfile();
	}

	public void addMyNode(NodeInfo nodeInfo) {
		client.send(new Message(MessageTypesEnum.ADD_ME, null, new AddMeData(nodeInfo)));
	}

	public void requestNodeConnection(NodeInfo nodeInfo, String messageId, int nbNodes) {
		client.send(new Message(MessageTypesEnum.REQUEST_CONNECTION, messageId, new RequestConnectionData(nodeInfo, nbNodes, true)));
	}
}
