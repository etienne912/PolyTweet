package com.polyTweet;

import com.polyTweet.dao.Node;
import com.polyTweet.dao.exceptions.MaxNodeException;
import com.polyTweet.model.Profile;
import org.junit.Test;

import java.net.BindException;

import static org.junit.Assert.assertEquals;

public class Tests {
	@Test
	public void profileTest() {
		Profile p = new Profile("Étienne", "Lécrivain");

		assertEquals("Étienne", p.getFirstName());
		assertEquals("Lécrivain", p.getLastName());

		assertEquals(p.getStatus(), "");
		p.setStatus("Codding an super new peer to peer software");
		assertEquals(p.getStatus(), "Codding an super new peer to peer software");

		assertEquals(p.getPosts().size(), 0);
		p.writePost("I wish a good day to all my PolyFollowers");
		assertEquals(p.getPosts().size(), 1);
		assertEquals(p.getPosts().get(0).getMessage(), "I wish a good day to all my PolyFollowers");
	}

	@Test
	public void searchTest() throws MaxNodeException, BindException {
		Profile profile1 = new Profile("Étienne", "Lécrivain");
		Node node1 = new Node(profile1, "127.0.0.1");

		Profile profile2 = new Profile("Lucas", "Hervouet");
		Node node2 = new Node(profile2, "127.0.0.2");

		Profile profile3 = new Profile("Alan", "Turing");
		Node node3 = new Node(profile3, "127.0.0.3");

		Profile profile4 = new Profile("Ada", "Lovelace");
		Node node4 = new Node(profile4, "127.0.0.4");

		node1.addNeighbor(node2.getNodeIp());
		node1.addNeighbor(node4.getNodeIp());

		node2.addNeighbor(node1.getNodeIp());
		node2.addNeighbor(node3.getNodeIp());
		node2.addNeighbor(node4.getNodeIp());

		node3.addNeighbor(node2.getNodeIp());

		node4.addNeighbor(node1.getNodeIp());
		node4.addNeighbor(node2.getNodeIp());

		assertEquals("Success find test", node3.getProfile(), node1.searchProfile(node3.getProfile().getId()));

//		assertThrows("Not found test", NodeNotFoundException.class, () -> node1.searchProfile(-1));

//		node2.addNeighbor(node4);
//		node2.addNeighbor(node4);
//		assertThrows("Max neighbor test", MaxNodeException.class, () -> node2.addNeighbor(node4));

		node1.close();
		node2.close();
		node3.close();
		node4.close();
	}
}
