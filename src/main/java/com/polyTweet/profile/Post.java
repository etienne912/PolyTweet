package com.polyTweet.profile;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private final Date date;
    private final String message, firstname, lastname;

    public Post(String message, Profile profile) {
        this.date = new Date();
        this.message = message;
        this.firstname = profile.getFirstName();
        this.lastname = profile.getLastName();
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return this.message;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }
}
