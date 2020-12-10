package com.polyTweet;

import com.polyTweet.node.Node;
import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.BindException;

import static org.junit.Assert.assertNull;

public class TestS3 {

	private Node node1, node2, node3, node4;
	private Profile profile1, profile2, profile3, profile4;

	@Before
	public void initObjects() throws BindException {
		profile1 = new Profile("P1", "N1");
		node1 = new Node(profile1, "127.0.0.1");

		profile2 = new Profile("P2", "N2");
		node2 = new Node(profile2, "127.0.0.2");

		profile3 = new Profile("P3", "N3");
		node3 = new Node(profile3, "127.0.0.3");

		profile4 = new Profile("P4", "N3");
		node4 = new Node(profile4, "127.0.0.4");

		node1.addNeighbor(node2.getNodeIp());
		node2.addNeighbor(node3.getNodeIp());
	}

	@After
	public void closeNodes() {
		node1.close();
		node2.close();
		node3.close();
		node4.close();
	}

	@Test
	public void unavailableUserTest1() throws NodeNotFoundException {
		Profile profile = node1.searchProfile(-1);

		assertNull(profile);
	}

}
