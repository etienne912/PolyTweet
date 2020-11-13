import com.PolyTweet.Node.Node;
import com.PolyTweet.Profile.Profile;

public class Test {
	public static void main(String[] args) {
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

		System.out.println(node1.searchProfile(2));
	}
}
