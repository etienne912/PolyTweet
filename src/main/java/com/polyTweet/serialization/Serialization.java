package com.polyTweet.serialization;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

/**
 * Class allowing to serialize an Object
 */
public class Serialization {

    /**
     * Serialize constructor
     *
     * @param o Object to serialize
     * @param path File path of where to serialize the object
     */
    public static void serialize(Object o, String path) {
        try {
            File fichier = new File(path);

            // Open the file
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));

            // serialization of the object
            oos.writeObject(o);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
