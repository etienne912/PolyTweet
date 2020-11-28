package com.polyTweet;

import com.polyTweet.node.Node;
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
		node1 = new Node(profile1);

		profile2 = new Profile("P2", "N2");
		node2 = new Node(profile2);

		profile3 = new Profile("P3", "N3");
		node3 = new Node(profile3);

		node1.addNeighbor(node2);
		node2.addNeighbor(node3);
	}

	@Test
	public void rootingTest() throws NodeNotFoundException {
		assertEquals(node1.searchProfile(node3.getId()), profile3);
	}
}
