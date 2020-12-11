package com.polyTweet.utils.serialization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Class allowing to serialize an Object
 */
public class Serialization {

	/**
	 * Serialize constructor
	 *
	 * @param o    Object to serialize
	 * @param path File path of where to serialize the object
	 */
	public static void serialize(Serializable o, String path) {
		try {
			new File("profiles").mkdir();
			File fichier = new File(path);

			// Open the file
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));

			// serialization of the object
			oos.writeObject(o);
		} catch (Exception ignored) {
		}
	}

}
