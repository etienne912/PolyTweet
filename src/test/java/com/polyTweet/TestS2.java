package com.polyTweet;

import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
import com.polyTweet.model.ProfileCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestS2 {

	private Node node1, node2, node3;
	private Profile profile1, profile2, profile3;

	@Before
	public void initObjects() throws IOException {
		profile1 = new Profile("P1", "N1");
		node1 = new Node(profile1, "127.0.2.1");

		profile2 = new Profile("P2", "N2");
		node2 = new Node(profile2, "127.0.2.2");

		profile3 = new Profile("P3", "N3");
		node3 = new Node(profile3, "127.0.2.3");

		node1.addNeighbor(node2.getNodeIp());
		node2.addNeighbor(node3.getNodeIp());
		node2.searchProfile(node3.getProfile().getId());
	}

	@After
	public void closeNodes() {
		node1.close();
		node2.close();
		node3.close();
	}

	@Test
	public void cacheTest1() {
		Profile profile = node1.searchProfile(profile3.getId());

		assertNotNull(profile);
		assertEquals(profile3.getId(), profile.getId());
		assertFalse(profile instanceof ProfileCache);
	}

	@Test
	public void cacheTest2() {
		node3.close();
		Profile profile = node1.searchProfile(profile3.getId());

		assertNotNull(profile);
		assertEquals(profile3.getId(), profile.getId());
		assertTrue(profile instanceof ProfileCache);
	}

	@Test
	public void cacheTest3() {
		node3.close();
		node2.flushCache();
		Profile profile = node1.searchProfile(profile3.getId());

		assertNull(profile);
	}
}
