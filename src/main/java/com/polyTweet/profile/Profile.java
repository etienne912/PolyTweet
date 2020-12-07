package com.polyTweet.profile;

import java.io.Serializable;
import java.util.*;

public class Profile implements Serializable {
	private static long count = 1;
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

	public void updateStatus() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Write your status :\n");

		this.status = scanner.nextLine();
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void writePost() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Write your post :\n");

		this.posts.add(new Post(scanner.nextLine()));
	}

	public void writePost(String message) {
		this.posts.add(new Post(message));
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Profile profile = (Profile) o;
		return id == profile.id &&
				Objects.equals(firstName, profile.firstName) &&
				Objects.equals(lastName, profile.lastName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName);
	}
}
