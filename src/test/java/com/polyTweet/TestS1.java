package com.polyTweet;

import com.polyTweet.node.Node;
import com.polyTweet.node.NodeInfo;
import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestS1 {

	private Node node1, node2, node3;
	private Profile profile1, profile2, profile3;

	@Before
	public void initObjects() {
		profile1 = new Profile("P1", "N1");
		node1 = new Node(profile1, new NodeInfo("127.0.0.1", 8000));

		profile2 = new Profile("P2", "N2");
		node2 = new Node(profile2, new NodeInfo("127.0.0.1", 8001));

		profile3 = new Profile("P3", "N3");
		node3 = new Node(profile3, new NodeInfo("127.0.0.1", 8002));

		node1.addNeighbor(node2.getNodeInfo());
		node2.addNeighbor(node3.getNodeInfo());
	}

	@Test
	public void rootingTest() throws NodeNotFoundException {
		assertEquals(node1.searchProfile(3), profile3);
	}
}
