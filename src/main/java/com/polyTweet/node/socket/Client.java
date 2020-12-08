package com.polyTweet.node.socket;

import com.polyTweet.node.NodeInfo;
import com.polyTweet.node.message.Message;
import com.polyTweet.node.message.MessageTypesEnum;
import com.polyTweet.node.message.data.CloseConnectionData;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {
	private static final int PORT = 8000;

	private Socket connexion = null;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	private final NodeInfo nodeInfo;

	public Client(NodeInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
		try {
			connexion = new Socket(nodeInfo.getIp(), Objects.requireNonNullElse(nodeInfo.getPort(), PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Message send(Message message) {
//		new Thread(() -> {
		try {
			outputStream = new ObjectOutputStream(connexion.getOutputStream());

//			System.out.println(nodeInfo + " Send : " + message.getType());
			outputStream.writeObject(message);
			outputStream.flush();

			inputStream = new ObjectInputStream(connexion.getInputStream());
			Message response = (Message) inputStream.readObject();

//			System.out.println(nodeInfo + " Receive response : " + response.getType());

			return response;

		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
//		}).start();

		return null;
	}

	public void close() throws IOException {
		outputStream = new ObjectOutputStream(connexion.getOutputStream());
		outputStream.writeObject(new Message(MessageTypesEnum.CLOSE_CONNECTION, null, new CloseConnectionData(nodeInfo)));
		outputStream.flush();
		connexion.close();
	}

}