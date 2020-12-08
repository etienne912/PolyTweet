package com.polyTweet.node.adapter;

import com.polyTweet.node.NodeInfo;
import com.polyTweet.node.message.Message;
import com.polyTweet.node.message.MessageTypesEnum;
import com.polyTweet.node.message.data.*;
import com.polyTweet.node.socket.Client;
import com.polyTweet.profile.Profile;

import java.io.IOException;
import java.util.List;

public class ClientAdapter {

	private final Client client;

	public ClientAdapter(NodeInfo nodeInfo) {
		client = new Client(nodeInfo);
	}

	public Profile searchProfile(long id, String messageId, boolean broadcast) {
		ReturnProfileData result = (ReturnProfileData) client.send(new Message(MessageTypesEnum.SEARCH_PROFILE, messageId, new SearchProfileData(id, broadcast))).getData();

		return result.getProfile();
	}

	public List<Profile> searchProfile(String name, String messageId) {
		ReturnProfilesData result = (ReturnProfilesData) client.send(new Message(MessageTypesEnum.SEARCH_PROFILE_BY_NAME, messageId, new SearchProfileByNameData(name))).getData();

		return result.getProfiles();
	}

	public void addMyNode(NodeInfo nodeInfo) {
		client.send(new Message(MessageTypesEnum.ADD_ME, null, new AddMeData(nodeInfo)));
	}

	public void requestNodeConnection(NodeInfo nodeInfo, String messageId, int nbNodes) {
		client.send(new Message(MessageTypesEnum.REQUEST_CONNECTION, messageId, new RequestConnectionData(nodeInfo, nbNodes, true)));
	}

	public void close() {
		try {
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
