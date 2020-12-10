package com.polyTweet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Represent the post written by the users
 */
public class Post implements Serializable {
    private final Date writtenDate;
    private final String message;
    private final long writerId;

    public Post(String message, long profileId) {
        this.writtenDate = new Date();
        this.message = message;
        this.writerId = profileId;
    }

    public Date getWrittenDate() {
        return writtenDate;
    }

    public String getMessage() {
        return this.message;
    }

    public long getWriterId() {
        return this.writerId;
    }
}
