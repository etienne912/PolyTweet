package com.polyTweet.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Profile implements Serializable {
	private static long count = 0;
	private final long id;
	private final String firstName, lastName;
	private String status;
	private final List<Post> posts;

	public Profile(String firstName, String lastName) {
		this.id = count++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = "";
		this.posts = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void writePost(String message) {
		this.posts.add(new Post(new Date(), message));
	}

	@Override
	public String toString() {
		return "Profile{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", statut='" + status + '\'' +
				", posts=" + posts +
				'}';
	}
}
