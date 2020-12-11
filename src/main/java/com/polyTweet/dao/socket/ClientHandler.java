package com.polyTweet.dao.socket;

import com.polyTweet.dao.adapter.ServerAdapter;
import com.polyTweet.dao.message.Message;
import com.polyTweet.dao.message.MessageTypesEnum;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * This class is used to process separately for each connection the messages received by another node
 */
public class ClientHandler implements Runnable {

	private final Socket sock;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	private final ServerAdapter serverAdapter;

	public ClientHandler(Socket pSock, ServerAdapter pServerAdapter) {
		sock = pSock;
		serverAdapter = pServerAdapter;
	}

	public void run() {

		while (!sock.isClosed()) {
			try {
				inputStream = new ObjectInputStream(sock.getInputStream());

				Message response = (Message) inputStream.readObject();

				Message toSend = serverAdapter.adapt(response);

				if (response.getType() == MessageTypesEnum.CLOSE_CONNECTION) {
					this.close();
					break;
				}

				outputStream = new ObjectOutputStream(sock.getOutputStream());

				outputStream.writeObject(toSend);
				outputStream.flush();
			} catch (SocketException e) {
				System.err.println("Connection lost !");
				break;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}


	public void close() {
		try {
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}