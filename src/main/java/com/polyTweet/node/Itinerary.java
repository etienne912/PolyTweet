package com.polyTweet.node;

import java.util.ArrayList;
import java.util.List;

public class Itinerary implements Cloneable {

	private ArrayList<Long> ids;

	public Itinerary() {
		this.ids = new ArrayList<>();
	}

	public Itinerary(List<Long> id) {
		this.ids = new ArrayList<>(id);
	}

	/**
	 * This function allows you to know if you have already passed through this node
	 *
	 * @param id The id of the Node we want to know if we've been there before
	 * @return True if we already passed through
	 */
	public boolean hasAlreadyPassed(Long id) {
		return this.ids.contains(id);
	}

	/**
	 * This fonction add a node id to the itinerary
	 *
	 * @param id The road we want to add
	 * @return The new itinerary
	 */
	public Itinerary addNodeId(Long id) {
		this.ids.add(id);

		return this;
	}

	@Override
	public String toString() {
		return this.ids.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Itinerary clone = (Itinerary) super.clone();

		clone.ids = new ArrayList<>();

		for (long id : this.ids) {
			clone.ids.add(id);
		}

		return clone;
	}
}
