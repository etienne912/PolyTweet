package com.polyTweet;

import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TestS3 {

	private Node node1, node2, node3, node4;
	private Profile profile1, profile2, profile3, profile4;

	@Before
	public void initObjects() throws IOException {
		profile1 = new Profile("P1", "N1");
		node1 = new Node(profile1, "127.0.3.1");

		profile2 = new Profile("P2", "N2");
		node2 = new Node(profile2, "127.0.3.2");

		profile3 = new Profile("P3", "N3");
		node3 = new Node(profile3, "127.0.3.3");

		profile4 = new Profile("P4", "N4");
		node4 = new Node(profile4, "127.0.3.4");

		node1.addNeighbor(node2.getNodeIp());
		node2.addNeighbor(node3.getNodeIp());
		node3.addNeighbor(node4.getNodeIp());
	}

	@After
	public void closeNodes() {
		node1.close();
		node2.close();
		node3.close();
		node4.close();
	}

	@Test
	public void unavailableUserTest1() {
		Profile profile = node1.searchProfile(-1);

		assertNull(profile);
	}

	@Test
	public void searchByNameTest1() {
		List<Profile> profiles = node1.searchProfile("P5");

		assertTrue(profiles.isEmpty());

		profiles = node1.searchProfile("P2");

		assertEquals(1, profiles.size());
		assertEquals("P2", profiles.get(0).getFirstName());

		profiles = node1.searchProfile("P4");

		assertEquals(1, profiles.size());
		assertEquals("P4", profiles.get(0).getFirstName());

		profiles = node1.searchProfile("P");

		assertEquals(4, profiles.size());
	}

	@Test
	public void searchByNameTest2() throws IOException {
		node1.requestNodeConnection();

		List<Profile> profiles = node1.searchProfile("P5");

		assertTrue(profiles.isEmpty());

		profiles = node1.searchProfile("P2");

		assertEquals(1, profiles.size());
		assertEquals("P2", profiles.get(0).getFirstName());

		profiles = node1.searchProfile("P4");

		assertEquals(1, profiles.size());
		assertEquals("P4", profiles.get(0).getFirstName());

		profiles = node1.searchProfile("P");

		assertEquals(4, profiles.size());
	}


}
