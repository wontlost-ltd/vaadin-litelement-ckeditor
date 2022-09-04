package com.wontlost.ckeditor.mention;

import java.util.List;

/**
 * @author Ryan Pang (ryan.pang@wontlost.com)
 */
public class MentionFeed {

    List<MentionFeedItem> feed;

    String marker;

    Integer minimumCharacters;

    public List<MentionFeedItem> getFeed() {
        return feed;
    }

    public void setFeed(List<MentionFeedItem> feed) {
        this.feed = feed;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public Integer getMinimumCharacters() {
        return minimumCharacters;
    }

    public void setMinimumCharacters(Integer minimumCharacters) {
        this.minimumCharacters = minimumCharacters;
    }

}
