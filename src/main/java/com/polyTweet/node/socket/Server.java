package com.polyTweet.node.socket;

import com.polyTweet.node.NodeInfo;
import com.polyTweet.node.adapter.ServerAdapter;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Objects;

public class Server {
	private static final int PORT = 8000;

	private ServerSocket server = null;
	private boolean isRunning = true;
	private ServerAdapter serverAdapter;
	private HashMap<NodeInfo, ClientHandler> clientHandlers;

	public Server(NodeInfo nodeInfo, ServerAdapter pServerAdapter) {
		try {
			server = new ServerSocket(Objects.requireNonNullElse(nodeInfo.getPort(), PORT), 5, InetAddress.getByName(nodeInfo.getIp()));
			serverAdapter = pServerAdapter;
			clientHandlers = new HashMap<>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open() {

		Thread t = new Thread(() -> {
			while (isRunning) {
				try {
					Socket client = server.accept();

					ClientHandler clientHandler = new ClientHandler(client, serverAdapter);

					InetSocketAddress remoteSocketAddress = (InetSocketAddress) client.getRemoteSocketAddress();
					this.clientHandlers.put(new NodeInfo(remoteSocketAddress.getAddress().getHostAddress(), remoteSocketAddress.getPort()), clientHandler);

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

	public void close(NodeInfo nodeInfo) {
		this.clientHandlers.get(nodeInfo).close();
		this.clientHandlers.remove(nodeInfo);
	}

	public void close() {
		isRunning = false;
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
