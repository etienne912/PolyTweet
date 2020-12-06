import com.polyTweet.node.Node;
import com.polyTweet.profile.Profile;
import com.polyTweet.serialization.Deserialization;
import com.polyTweet.serialization.Serialization;

public class Main {
	public static void main(String[] args) {

		System.out.println("\n*********** Deserialization ***********\n");

		Profile profile0;
		profile0 = (Profile) Deserialization.deserialize("./tmp/myProfile.ser");
		if (profile0 == null) {
			profile0 = new Profile("Étienne", "Lécrivain");
		}

		System.out.println("\n*********** Create Profile ***********\n");

		Node node0 = new Node(profile0);

		Profile profile1 = new Profile("Lucas", "Hervouet");
		Node node1 = new Node(profile1, node0);

		Profile profile2 = new Profile("Alan", "Turing");
		Node node2 = new Node(profile2, node0);

		Profile profile3 = new Profile("Ada", "Lovelace");
		Node node3 = new Node(profile3, node2);

		Profile profile4 = new Profile("Claude", "Shannon");
		Node node4 = new Node(profile4, node1);

		Profile profile5 = new Profile("Linus", "Torvalds");
		Node node5 = new Node(profile5, node4);

		Profile profile6 = new Profile("Richard", "Stallman");
		Node node6 = new Node(profile6, node4);

		System.out.println("\n*********** Search a Profile ***********\n");

		try {
			System.out.println(node1.searchProfile(3));
			System.out.println(node1.searchProfile(2));

			System.out.println(node0);
			System.out.println(node1);
			System.out.println(node2);
			System.out.println(node3);
			System.out.println(node4);
			System.out.println(node5);
			System.out.println(node6);
		} catch (Exception e) {
			System.err.println(e);
		}

		System.out.println("\n*********** Following ***********\n");

		node2.addFollow(0);
		node2.getProfileFollowedInformation().forEach(profile -> {
			System.out.println(profile.toString());
		});

		System.out.println("\n*********** Update Profile ***********\n");

		profile0.updateStatus();
		profile0.writePost();

		System.out.println("\n*********** Serialisation ***********\n");

		Serialization.serialize(profile0, "./tmp/profile-0.ser");

		System.exit(0);
	}
}
