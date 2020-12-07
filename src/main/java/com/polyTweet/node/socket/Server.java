package com.polyTweet.node.socket;

import com.polyTweet.node.NodeInfo;
import com.polyTweet.node.adapter.ServerAdapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {
	private static final int PORT = 8000;

	private ServerSocket server = null;
	private boolean isRunning = true;
	private ServerAdapter serverAdapter;

	public Server(NodeInfo nodeInfo, ServerAdapter pServerAdapter) {
		try {
			server = new ServerSocket(Objects.requireNonNullElse(nodeInfo.getPort(), PORT), 5, InetAddress.getByName(nodeInfo.getIp()));
			serverAdapter = pServerAdapter;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open() {

		Thread t = new Thread(() -> {
			while (isRunning) {
				try {
					Socket client = server.accept();

					Thread t1 = new Thread(new ClientHandler(client, serverAdapter));
					t1.start();

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

	public void close() {
		isRunning = false;
	}
}
