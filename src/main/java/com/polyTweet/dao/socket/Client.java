package com.polyTweet.dao.socket;

import com.polyTweet.dao.message.Message;
import com.polyTweet.dao.message.MessageTypesEnum;
import com.polyTweet.dao.message.data.CloseConnectionData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class is used to create a socket connection to other nodes
 */
public class Client {
	private static final int PORT = 8000;

	private Socket connexion = null;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;

	public Client(String nodeIp) throws ConnectException, UnknownHostException {
		try {
			connexion = new Socket(nodeIp, PORT);
		} catch (ConnectException | UnknownHostException exception) {
			throw exception;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used to send message over the socket connection
	 *
	 * @param message Message to send
	 * @return The response of the other node
	 */
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

	/**
	 * Used to close the connection, both from this node to the other node and from node to node
	 *
	 * @param myIp My IP address
	 */
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