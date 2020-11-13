import com.PolyTweet.Node.Node;
import com.PolyTweet.Profile.Profile;

public class Main {
	public static void main(String[] args) {

		Profile myProfile = new Profile("Étienne", "Lécrivain");
		Node myNode = new Node(myProfile);

		System.out.println(myProfile);
	}
}
