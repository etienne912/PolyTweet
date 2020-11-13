package com.PolyTweet.Node;

import com.PolyTweet.Profile.Profile;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private List<Neighbor> neighbors;
	private List<ProfileCache> cache;
	private Profile myProfile;

	public Node(Profile myProfile) {
		this.neighbors = new ArrayList<>();
		this.cache = new ArrayList<>();
		this.myProfile = myProfile;
	}

	public Profile getProfile(long id) {
		return null;
	}

}
