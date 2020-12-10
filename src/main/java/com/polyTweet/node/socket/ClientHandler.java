package com.polyTweet.node.socket;

import com.polyTweet.node.adapter.ServerAdapter;
import com.polyTweet.node.message.Message;
import com.polyTweet.node.message.MessageTypesEnum;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

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
//		System.out.println("Start client " + sock.getRemoteSocketAddress().toString());

		while (!sock.isClosed()) {
			try {
				inputStream = new ObjectInputStream(sock.getInputStream());

				Message response = (Message) inputStream.readObject();
				String remoteAddress = ((InetSocketAddress) sock.getRemoteSocketAddress()).getAddress().getHostAddress();
//				System.out.println("From : " + remoteAddress + " type : " + response.getType());

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
				System.err.println(this.sock.getRemoteSocketAddress().toString());
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