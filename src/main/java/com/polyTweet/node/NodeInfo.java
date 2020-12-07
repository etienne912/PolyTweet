package com.polyTweet.node;

import java.io.Serializable;
import java.util.Objects;

public class NodeInfo implements Serializable {

	private final String ip;
	private final Integer port;

	public NodeInfo(String ip, Integer port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public Integer getPort() {
		return port;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NodeInfo nodeInfo = (NodeInfo) o;
		return Objects.equals(ip, nodeInfo.ip) && Objects.equals(port, nodeInfo.port);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ip, port);
	}

	@Override
	public String toString() {
		return "{ip='" + ip + '\'' + ", port=" + port + '}';
	}
}
