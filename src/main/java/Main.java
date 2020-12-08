import com.polyTweet.node.Node;
import com.polyTweet.node.NodeInfo;
import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Profile;
import com.polyTweet.serialization.Deserialization;
import com.polyTweet.serialization.Serialization;

public class Main {
	public static void main(String[] args) {

		System.out.println("\n*********** Deserialization ***********\n");

		Profile profile0;
		profile0 = (Profile) Deserialization.deserialize("./profiles/myProfile.ser");
		if (profile0 == null) {
			System.err.println("C'est nuuuuul !!");
			profile0 = new Profile("Étienne", "Lécrivain");
		}

		System.out.println("\n*********** Create Profile ***********\n");

		Node node0 = new Node(profile0, new NodeInfo("127.0.0.1", 20000));

		Profile profile1 = new Profile("Lucas", "Hervouet");
		Node node1 = new Node(profile1, new NodeInfo("127.0.0.2", 20000));

		node0.addNeighbor(node1.getNodeInfo());

		Profile profile2 = new Profile("Alan", "Turing");
		Node node2 = new Node(profile2, new NodeInfo("127.0.0.3", 20000));

		node2.addNeighbor(node1.getNodeInfo());
//
		Profile profile3 = new Profile("Ada", "Lovelace");
		Node node3 = new Node(profile3, new NodeInfo("127.0.0.4", 20000));
		node3.addNeighbor(node1.getNodeInfo());

		Profile profile4 = new Profile("Claude", "Shannon");
		Node node4 = new Node(profile4, new NodeInfo("127.0.0.5", 20000));
		node4.addNeighbor(node2.getNodeInfo());

//		Profile profile5 = new Profile("Linus", "Torvalds");
//		Node node5 = new Node(profile5, "127.0.0.1", node4.getIp());
//
//		Profile profile6 = new Profile("Richard", "Stallman");
//		Node node6 = new Node(profile6, "127.0.0.1", node4.getIp());

		System.out.println("\n*********** Search a Profile ***********\n");

		try {
			System.out.println(node0.searchProfile(2));
//			System.out.println(node1.searchProfile(2));

		} catch (NodeNotFoundException e) {
			System.err.println(e);
		}
		System.out.println(node0);
		System.out.println(node1);
		System.out.println(node2);
		System.out.println(node3);
		System.out.println(node4);
//		System.out.println(node5);
//		System.out.println(node6);

//		System.out.println("\n*********** Following ***********\n");

//		node2.addFollow(0);
//		System.out.println(node2.getProfileFollowedInformation());

//		System.out.println("\n*********** Update Profile ***********\n");

//		profile0.updateStatus();
//		profile0.writePost();

		System.out.println("\n*********** Serialisation ***********\n");

		Serialization.serialize(profile0, "./profiles/myProfile.ser");

//		System.exit(0);
	}
}
