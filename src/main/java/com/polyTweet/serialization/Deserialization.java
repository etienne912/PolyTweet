package com.polyTweet.serialization;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

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
            File fichier = new File(path);

            // Open the file
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));

            return ois.readObject();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}