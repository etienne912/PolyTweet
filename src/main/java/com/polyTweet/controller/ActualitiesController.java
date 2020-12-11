package com.polyTweet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.polyTweet.Observable;
import com.polyTweet.Observer;
import com.polyTweet.model.Post;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ActualitiesController implements Initializable, Observer {

	private final Profile profile;
	private List<Profile> profiles;

	@FXML
	public JFXTextArea postText;
	public Button postButton, refreshButton;
	public VBox vboxPost;

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

		if (this.vboxPost != null) this.vboxPost.getChildren().clear();

		sortedPosts.forEach((post) -> {

			BorderPane postPane = new BorderPane();

			Profile profileVisit = profile;

			if (post.getWriterId() != profile.getId()) {
				profileVisit = MainView.getNode().searchProfile(post.getWriterId());
			}

			if (profileVisit != null) {

				JFXButton button = new JFXButton(profileVisit.getFirstName() + " " + profileVisit.getLastName());
				button.setOnAction(this::visitProfileClick);
				button.getStyleClass().add("profileButton");

				Label label = new Label(post.getWrittenDate().toString() + " - " + post.getMessage());
				label.getStyleClass().add("postLabel");

				postPane.setTop(button);
				postPane.setCenter(label);

				vboxPost.getChildren().add(postPane);

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
			if (p != null) {
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