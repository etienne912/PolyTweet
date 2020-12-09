package com.polyTweet;

import com.polyTweet.node.Node;
import com.polyTweet.node.NodeInfo;
import com.polyTweet.node.exceptions.MaxNodeException;
import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class Tests {
	@Test
	public void profileTest() {
		Profile p = new Profile("Étienne", "Lécrivain");

		assertEquals("Étienne", p.getFirstName());
		assertEquals("Lécrivain", p.getLastName());

		assertEquals(p.getStatus(), "");
//		p.setStatus("Codding an super new peer to peer software");
//		assertEquals(p.getStatus(), "Codding an super new peer to peer software");

		assertEquals(p.getPosts().size(), 0);
//		p.writePost("I wish a good day to all my PolyFollowers");
//		assertEquals(p.getPosts().size(), 1);
//		assertEquals(p.getPosts().get(0).getMessage(), "I wish a good day to all my PolyFollowers");
	}

	@Test
	public void searchTest() throws MaxNodeException, NodeNotFoundException {
		Profile profile1 = new Profile("Étienne", "Lécrivain");
		Node node1 = new Node(profile1, new NodeInfo("127.0.0.1", 9000));

		Profile profile2 = new Profile("Lucas", "Hervouet");
		Node node2 = new Node(profile2, new NodeInfo("127.0.0.1", 9001));

		Profile profile3 = new Profile("Alan", "Turing");
		Node node3 = new Node(profile3, new NodeInfo("127.0.0.1", 9002));

		Profile profile4 = new Profile("Ada", "Lovelace");
		Node node4 = new Node(profile4, new NodeInfo("127.0.0.1", 9003));

		node1.addNeighbor(node2.getNodeInfo());
		node1.addNeighbor(node4.getNodeInfo());

		node2.addNeighbor(node1.getNodeInfo());
		node2.addNeighbor(node3.getNodeInfo());
		node2.addNeighbor(node4.getNodeInfo());

		node3.addNeighbor(node2.getNodeInfo());

		node4.addNeighbor(node1.getNodeInfo());
		node4.addNeighbor(node2.getNodeInfo());

		assertEquals("Success find test", node1.searchProfile(node3.getProfile().getId()), node3.getProfile());

//		assertThrows("Not found test", NodeNotFoundException.class, () -> node1.searchProfile(-1));

//		node2.addNeighbor(node4);
//		node2.addNeighbor(node4);
//		assertThrows("Max neighbor test", MaxNodeException.class, () -> node2.addNeighbor(node4));
	}
}
