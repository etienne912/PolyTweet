package com.polyTweet.profile;

import com.polyTweet.view.MainView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Profile implements Serializable {
	private final long id;
	private String firstName, lastName, status;
	private final List<Post> posts;
	private final ArrayList<Long> profileFollowed;

	public Profile(String firstName, String lastName) {
		this.id = Objects.hash(firstName, lastName, new Date());
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = "";
		this.posts = new ArrayList<>();
		profileFollowed = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		MainView.update();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		MainView.update();
	}

	public void setStatus(String status) {
		this.status = status;
		MainView.update();
	}


	public List<Post> getPosts() {
		return posts;
	}

	public void writePost(String message) {
		this.posts.add(new Post(message, this.id));
		MainView.update();
	}

	public ArrayList<Long> getProfileFollowed() {
		return profileFollowed;
	}

	public void unfollow(Long id) {
		this.profileFollowed.remove(id);
		MainView.updateProfileVisitor();
	}

	public void follow(Long id) {
		this.profileFollowed.add(id);
		MainView.updateProfileVisitor();
	}

	public boolean isFollowing(Long id) {
		return this.profileFollowed.contains(id);
	}

	@Override
	public String toString() {
		return "Profile{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", statut='" + status + '\'' +
				", posts=" + posts.toString() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Profile profile = (Profile) o;
		return id == profile.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
