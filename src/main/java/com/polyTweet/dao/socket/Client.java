package com.polyTweet.dao.socket;

import com.polyTweet.dao.message.Message;
import com.polyTweet.dao.message.MessageTypesEnum;
import com.polyTweet.dao.message.data.CloseConnectionData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private static final int PORT = 8000;

	private Socket connexion = null;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	private final String nodeIp;

	public Client(String nodeIp) {
		this.nodeIp = nodeIp;
		try {
			connexion = new Socket(nodeIp, PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Message send(Message message) {
//		new Thread(() -> {
		try {
			outputStream = new ObjectOutputStream(connexion.getOutputStream());

			outputStream.writeObject(message);
			outputStream.flush();

			inputStream = new ObjectInputStream(connexion.getInputStream());


			return (Message) inputStream.readObject();

		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
//		}).start();

		return null;
	}

	public void close(String myIp) {
		try {
			outputStream = new ObjectOutputStream(connexion.getOutputStream());
			outputStream.writeObject(new Message(MessageTypesEnum.CLOSE_CONNECTION, null, new CloseConnectionData(myIp)));
			outputStream.flush();
			connexion.close();
		} catch (IOException ignored) {
		}
	}

}