package com.polyTweet;

import com.polyTweet.dao.Node;
import com.polyTweet.dao.exceptions.NodeNotFoundException;
import com.polyTweet.model.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestS5 {

	private Node node1, node2, node3;
	private Profile profile1, profile2, profile3;

	@Before
	public void initObjects() throws IOException {
		profile1 = new Profile("P1", "N1");
		node1 = new Node(profile1, "127.0.0.1");

		profile2 = new Profile("P2", "N2");
		node2 = new Node(profile2, "127.0.0.2");

		profile3 = new Profile("P3", "N3");
		node3 = new Node(profile3, "127.0.0.3");

		node1.addNeighbor(node2.getNodeIp());
		node2.addNeighbor(node3.getNodeIp());
	}

	@After
	public void closeNodes() {
		node1.close();
		node2.close();
		node3.close();
	}

	@Test
	public void shareRootingInfoTest1() throws NodeNotFoundException {
		node1.requestNodeConnection();
		node2.close();

		Profile profile = node1.searchProfile(profile3.getId());

		assertNotNull(profile);
		assertEquals(profile3.getId(), profile.getId());
	}

}
