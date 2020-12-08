package com.polyTweet.controller;

import com.polyTweet.node.Node;
import com.polyTweet.node.exceptions.NodeNotFoundException;
import com.polyTweet.profile.Post;
import com.polyTweet.profile.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class ActualitiesController implements Initializable {

	private Profile profile;
	private Node node;
	private List<Profile> profiles;

	@FXML
	public TextField postText;
	public Button postButton, refreshButton;
	public VBox actualitiesPosts;

	public ActualitiesController() {
		this.profile = MainView.getProfile();
		this.node = MainView.getNode();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initPost();
	}

	private void initPost() {
		List<Post> sortedPosts = new ArrayList<>();

		this.profiles = this.node.getProfileFollowedInformation();
		this.profiles.add(profile);

		this.profiles.forEach(pf -> {
			if (pf != null) {
				List<Post> posts = pf.getPosts();
				sortedPosts.addAll(posts);
			}
		});

		sortedPosts.sort(Comparator.comparing(Post::getDate));

		this.actualitiesPosts.getChildren().clear();

		sortedPosts.forEach((post) -> {

			try {
				GridPane grid = new GridPane();

				Profile profileVisit = profile;

				System.out.println(post.getProfileId());

				if (post.getProfileId() != profile.getId()) {

					profileVisit = this.node.searchProfile(post.getProfileId());
					System.out.println(profileVisit);
				}

				if (profileVisit != null) {

					Button button = new Button(profileVisit.getFirstName() + " " + profileVisit.getLastName());
					button.setOnAction(this::visitProfileClick);

					Label label = new Label(post.getDate().toString() + " - " + post.getMessage());

					grid.addRow(0, button);
					grid.addRow(1, label);

					actualitiesPosts.getChildren().add(grid);

				}
			} catch (NodeNotFoundException e) {
				e.printStackTrace();
			}

		});
	}

	@FXML
	public void postClick(ActionEvent e) {
		String post = this.postText.getText();
		if (!post.equals("")) {
			this.postText.setText("");
			profile.writePost(post);
		}
	}

	@FXML
	public void refresh(ActionEvent e) {
		this.initPost();
	}

	@FXML
	public void visitProfileClick(ActionEvent e) {

		Button button = (Button) e.getTarget();
		String[] entireName = button.getText().split(" ");

		for (Profile p : profiles) {
			if (entireName[0].equals(p.getFirstName()) && entireName[1].equals(p.getLastName())) {
				if (p.equals(profile)) {
					MainView.switchScene("profile");
				} else {
					MainView.initVisitProfile(p);
					MainView.switchScene("profileVisitor");
				}
				break;
			}
		}

	}

	public void update() {
		this.initPost();
	}

}