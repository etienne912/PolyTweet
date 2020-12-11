package com.polyTweet.model;

import com.polyTweet.Observable;
import com.polyTweet.Observer;

import java.io.Serializable;
import java.util.*;

/**
 * Represents the user profile
 */
public class Profile implements Serializable, Observable {
	private final long id;
	private String firstName, lastName, status;
	private final List<Post> posts;
	private final HashSet<Long> followedProfiles;
	private final ArrayList<Observer> observers;

	public Profile(String firstName, String lastName) {
		this.id = Objects.hash(firstName, lastName, new Date());
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = "";
		this.posts = new ArrayList<>();
		this.followedProfiles = new HashSet<>();
		observers = new ArrayList<>();
	}

	/**
	 * Constructor by copy
	 *
	 * @param profile Profile to copy
	 */
	public Profile(Profile profile) {
		this.id = profile.id;
		this.firstName = profile.firstName;
		this.lastName = profile.lastName;
		this.status = profile.status;
		this.posts = profile.posts;
		this.followedProfiles = profile.followedProfiles;
		observers = new ArrayList<>();
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
		this.notifyObserver();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.notifyObserver();
	}

	public void setStatus(String status) {
		this.status = status;
		this.notifyObserver();
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void writePost(String message) {
		this.posts.add(new Post(message, this.id));
		this.notifyObserver();
	}

	public HashSet<Long> getFollowedProfiles() {
		return followedProfiles;
	}

	public void unfollow(Long profileId) {
		this.followedProfiles.remove(profileId);
		this.notifyObserver();
	}

	public void follow(Long profileId) {
		this.followedProfiles.add(profileId);
		this.notifyObserver();
	}

	public boolean isFollowing(Long profileId) {
		return this.followedProfiles.contains(profileId);
	}

	@Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObserver() {
		this.observers.forEach(observer -> observer.update(this));
	}

	public void clearObservers() {
		this.observers.clear();
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
		if (o == null || (o.getClass() != Profile.class  && o.getClass() != ProfileCache.class)) return false;
		Profile profile = (Profile) o;
		return id == profile.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
