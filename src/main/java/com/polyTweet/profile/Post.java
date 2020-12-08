package com.polyTweet.profile;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private final Date date;
    private final String message;
    private final long id;

    public Post(String message, long profileId) {
        this.date = new Date();
        this.message = message;
        this.id = profileId;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return this.message;
    }

    public long getProfileId() {
        return this.id;
    }
}
