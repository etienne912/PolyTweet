package com.polyTweet.controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

/**
 * View Controller allowing to contain all views and easily switch from one view to another.
 */
public class ScreenController {
	private final HashMap<String, Pane> screenMap = new HashMap<>();
	private final Scene main;

	/**
	 * Controller Constructor.
	 */
	public ScreenController(Scene main) {
		this.main = main;
	}

	/**
	 * Function to add a new view.
	 * @param name Name of the view
	 * @param pane Panel which contains the view
	 */
	public void addScreen(String name, Pane pane) {
		screenMap.put(name, pane);
	}

	/**
	 * Function to remove a view.
	 * @param name Name of the view to remove
	 */
	public void removeScreen(String name) {
		screenMap.remove(name);
	}

	/**
	 * Function to switch view.
	 * @param name Name of the view to display
	 */
	public void activate(String name) {
		main.setRoot(screenMap.get(name));
	}
}
