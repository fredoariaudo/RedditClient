package com.aariaudo.android.redditclient.views;

import com.aariaudo.android.redditclient.model.RedditEntry;

import java.util.ArrayList;

public interface EntryListView
{
    int NUM_ITEMS_TO_LOAD = 25;
    int TOTAL_ITEMS_TO_SHOW = 50;

    void showProgress();
    void hideProgress();
    void enableSwipeGesture();
    void disableSwipeGesture();
    void addItems(ArrayList<RedditEntry> entries);
    void refreshItems();
    void loadMore();
    void onThumbnailClicked(RedditEntry redditEntry);
}
