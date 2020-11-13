import com.polyTweet.node.Node;
import com.polyTweet.profile.Profile;

public class Main {
	public static void main(String[] args) {

		Profile myProfile = new Profile("Étienne", "Lécrivain");
		Node myNode = new Node(myProfile);

		System.out.println(myProfile);
	}
}
