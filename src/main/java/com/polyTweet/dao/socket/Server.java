package com.polyTweet.dao.socket;

import com.polyTweet.dao.adapter.ServerAdapter;

import java.io.IOException;
import java.net.*;

/**
 * This class is used to process the connection creation request
 */
public class Server {
	private static final int PORT = 8000;
	private final ServerAdapter serverAdapter;
	private ServerSocket server = null;
	private boolean isRunning = true;

	/**
	 * Server's constructor
	 *
	 * @param nodeIp         Ip that the server is going to have
	 * @param pServerAdapter The message adapter for the server
	 * @throws BindException Throw if the address nodeIp is already in use
	 */
	public Server(String nodeIp, ServerAdapter pServerAdapter) throws BindException, UnknownHostException {
		try {
			server = new ServerSocket(PORT, 5, InetAddress.getByName(nodeIp));
		} catch (BindException | UnknownHostException exception) {
			throw exception;
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverAdapter = pServerAdapter;
	}

	/**
	 * Used to open the server. This will allow other nodes to create connections to this node.
	 */
	public void open() {

		Thread t = new Thread(() -> {
			while (isRunning) {
				try {
					Socket client = server.accept();

					Thread t1 = new Thread(new ClientHandler(client, serverAdapter));
					t1.start();

				} catch (SocketException ignored) {
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
				server = null;
			}
		});

		t.start();
	}

	/**
	 * Used to close the server properly
	 */
	public void close() {
		isRunning = false;
		try {
			if (server != null)
				server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
