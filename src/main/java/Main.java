import com.polyTweet.node.Node;
import com.polyTweet.profile.Profile;

public class Main {
	public static void main(String[] args) {

		Profile profile0 = new Profile("Étienne", "Lécrivain");
		System.out.println(profile0);
		Node node0 = new Node(profile0);

		Profile profile1 = new Profile("Lucas", "Hervouet");
		Node node1 = new Node(profile1);

		Profile profile2 = new Profile("Alan", "Turing");
		Node node2 = new Node(profile2);

		Profile profile3 = new Profile("Ada", "Lovelace");
		Node node3 = new Node(profile3);

		try {
			node0.searchEnterPoint(node1);

			node2.searchEnterPoint(node3);

			node3.searchEnterPoint(node0);

			System.out.println(node1.searchProfile(3));
			System.out.println(node1.searchProfile(2));

			System.out.println(node0);
			System.out.println(node1);
			System.out.println(node2);
			System.out.println(node3);
		} catch (Exception e) {
			System.err.println(e);
		}

		node2.addFollow(0);
		node2.getProfileFollowedInformation().forEach(profile -> {
			System.out.println(profile.toString());
		});

	}
}
