package com.wontlost.ckeditor.mention;

import java.util.List;

/**
 * @author Ryan Pang (ryan.pang@wontlost.com)
 */
public class MentionConfig {

    Integer[] commitKeys;

    Integer dropdownLimit;

    List<MentionFeed> feeds;

    public Integer[] getCommitKeys() {
        return commitKeys;
    }

    public void setCommitKeys(Integer[] commitKeys) {
        this.commitKeys = commitKeys;
    }

    public Integer getDropdownLimit() {
        return dropdownLimit;
    }

    public void setDropdownLimit(Integer dropdownLimit) {
        this.dropdownLimit = dropdownLimit;
    }

    public List<MentionFeed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<MentionFeed> feeds) {
        this.feeds = feeds;
    }

}
