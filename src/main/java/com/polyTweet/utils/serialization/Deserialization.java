package com.polyTweet.utils.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Class allowing to deserialize an Object
 */
public class Deserialization {

	/**
	 * Deserialize constructor
	 *
	 * @param path Path of the file containing the object to deserialize
	 * @return Object deserialized
	 */
	public static Object deserialize(String path) {
		try {
			File file = new File(path);

			// Open the file
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

			return ois.readObject();

		} catch (Exception ignored) {
		}

		return null;
	}

}
