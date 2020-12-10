package com.polyTweet.node.socket;

import com.polyTweet.node.adapter.ServerAdapter;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class Server {
	private static final int PORT = 8000;

	private ServerSocket server = null;
	private boolean isRunning = true;
	private final ServerAdapter serverAdapter;
	private final HashMap<String, ClientHandler> clientHandlers;

	public Server(String nodeIp, ServerAdapter pServerAdapter) throws BindException {
		try {
			server = new ServerSocket(PORT, 5, InetAddress.getByName(nodeIp));
		} catch (BindException bindException) {
			throw bindException;
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverAdapter = pServerAdapter;
		clientHandlers = new HashMap<>();
	}

	public void open() {

		Thread t = new Thread(() -> {
			while (isRunning) {
				try {
					Socket client = server.accept();

					ClientHandler clientHandler = new ClientHandler(client, serverAdapter);

					InetSocketAddress remoteSocketAddress = (InetSocketAddress) client.getRemoteSocketAddress();
					this.clientHandlers.put(remoteSocketAddress.getAddress().getHostAddress(), clientHandler);

					Thread t1 = new Thread(clientHandler);
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

	public void close(String nodeIp) {
		this.clientHandlers.get(nodeIp).close();
		this.clientHandlers.remove(nodeIp);
	}

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
