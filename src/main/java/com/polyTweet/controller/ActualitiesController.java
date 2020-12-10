package com.polyTweet.controller;

import com.polyTweet.Observable;
import com.polyTweet.Observer;
import com.polyTweet.dao.exceptions.NodeNotFoundException;
import com.polyTweet.model.Post;
import com.polyTweet.model.Profile;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ActualitiesController implements Initializable, Observer {

	private final Profile profile;
	private List<Profile> profiles;

	@FXML
	public TextField postText;
	public Button postButton, refreshButton;
	public VBox actualitiesPosts;

	public ActualitiesController() {
		this.profile = MainView.getProfile();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initPost();
	}

	private void initPost() {
		List<Post> sortedPosts = new ArrayList<>();

		this.profiles = MainView.getNode().getProfileFollowedInformation();
		this.profiles.add(profile);

		this.profiles.forEach(pf -> {
			if (pf != null) {
				List<Post> posts = pf.getPosts();
				sortedPosts.addAll(posts);
			}
		});

		sortedPosts.sort((p1, p2) -> p2.getWrittenDate().compareTo(p1.getWrittenDate()));

		this.actualitiesPosts.getChildren().clear();

		sortedPosts.forEach((post) -> {

			try {
				GridPane grid = new GridPane();

				Profile profileVisit = profile;

				if (post.getWriterId() != profile.getId()) {
					profileVisit = MainView.getNode().searchProfile(post.getWriterId());
				}

				if (profileVisit != null) {

					Button button = new Button(profileVisit.getFirstName() + " " + profileVisit.getLastName());
					button.setOnAction(this::visitProfileClick);

					Label label = new Label(post.getWrittenDate().toString() + " - " + post.getMessage());

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
			if( p != null ) {
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

	}

	@Override
	public void update(Observable observable) {
		this.initPost();
	}
}