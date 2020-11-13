package com.PolyTweet.Node;

public class Neighbor {
	private final String ip;
	private final String port;

	public Neighbor(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}
}
