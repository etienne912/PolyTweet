import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
import com.polyTweet.utils.serialization.Deserialization;
import com.polyTweet.utils.serialization.Serialization;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {

		System.out.println("\n*********** Deserialization ***********\n");

		Profile profile1;
		profile1 = (Profile) Deserialization.deserialize("./profiles/myProfile.ser");
		if (profile1 == null) {
			System.err.println("C'est nuuuuul !!");
			profile1 = new Profile("Étienne", "Lécrivain");
		}

		System.out.println("\n*********** Create Profile ***********\n");

		Node node1 = new Node(profile1, "127.0.0.1");

		Profile profile2 = new Profile("Lucas", "Hervouet");
		Node node2 = new Node(profile2, "127.0.0.2");

		node1.addNeighbor(node2.getNodeIp());

//		Profile profile3 = new Profile("Alan", "Turing");
//		Node node3 = new Node(profile3, "127.0.0.3");
//
//		node3.addNeighbor(node2.getNodeIp());

//		Profile profile4 = new Profile("Ada", "Lovelace");
//		Node node4 = new Node(profile4, "127.0.0.4");
//		node4.addNeighbor(node2.getNodeIp());
//
//		Profile profile5 = new Profile("Claude", "Shannon");
//		Node node5 = new Node(profile5, "127.0.0.5");
//		node5.addNeighbor(node3.getNodeIp());
//
//		node1.requestNodeConnection();

//		Profile profile6 = new Profile("Linus", "Torvalds");
//		Node node6 = new Node(profile6, "127.0.0.6");
//
//		Profile profile7 = new Profile("Richard", "Stallman");
//		Node node7 = new Node(profile7, "127.0.0.7");

		System.out.println("\n*********** Search a Profile ***********\n");

		System.out.println(node1.searchProfile(profile2.getId()));
		System.out.println(node1.searchProfile("l"));
//			System.out.println(node2.searchProfile(2));

		System.out.println(node1);
		System.out.println(node2);
//		System.out.println(node3);
//		System.out.println(node4);
//		System.out.println(node5);
//		System.out.println(node6);
//		System.out.println(node7);

//		System.out.println("\n*********** Following ***********\n");

//		node3.addFollow(0);
//		System.out.println(node3.getProfileFollowedInformation());

//		System.out.println("\n*********** Update Profile ***********\n");

//		profile1.updateStatus();
//		profile1.writePost();

		System.out.println("\n*********** Serialisation ***********\n");

		Serialization.serialize(profile1, "./profiles/myProfile.ser");

		node1.close();
		node2.close();
//		node3.close();

//		System.exit(0);
	}
}
