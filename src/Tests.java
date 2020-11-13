import com.PolyTweet.Node.Node;
import com.PolyTweet.Node.exceptions.MaxNodeException;
import com.PolyTweet.Node.exceptions.NodeNotFoundException;
import com.PolyTweet.Profile.Profile;
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
		p.setStatus("Codding an super new peer to peer software");
		assertEquals(p.getStatus(), "Codding an super new peer to peer software");

		assertEquals(p.getPosts().size(), 0);
		p.writePost("I wish a good day to all my PolyFollowers");
		assertEquals(p.getPosts().size(), 1);
		assertEquals(p.getPosts().get(0).getMessage(), "I wish a good day to all my PolyFollowers");
	}

	@Test
	public void searchTest() throws MaxNodeException, NodeNotFoundException {
		Profile profile1 = new Profile("Étienne", "Lécrivain");
		Node node1 = new Node(profile1);

		Profile profile2 = new Profile("Lucas", "Hervouet");
		Node node2 = new Node(profile2);

		Profile profile3 = new Profile("Alan", "Turing");
		Node node3 = new Node(profile3);

		Profile profile4 = new Profile("Ada", "Lovelace");
		Node node4 = new Node(profile4);

		node1.addNeighbor(node2);
		node1.addNeighbor(node4);

		node2.addNeighbor(node1);
		node2.addNeighbor(node3);
		node2.addNeighbor(node4);

		node3.addNeighbor(node2);

		node4.addNeighbor(node1);
		node4.addNeighbor(node2);

		assertEquals("Success find test", node1.searchProfile(2).getId(), node3.getProfile().getId());

		assertThrows("Not found test", NodeNotFoundException.class, () -> node1.searchProfile(-1));

		node2.addNeighbor(node4);
		node2.addNeighbor(node4);
		assertThrows("Max neighbor test", MaxNodeException.class, () -> node2.addNeighbor(node4));
	}
}
