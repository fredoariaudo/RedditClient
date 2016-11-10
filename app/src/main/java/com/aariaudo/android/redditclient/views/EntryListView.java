package com.aariaudo.android.redditclient.views;

import com.aariaudo.android.redditclient.model.RedditEntry;

import java.util.ArrayList;

public interface EntryListView
{
    void showProgress();
    void hideProgress();
    void addItems(ArrayList<RedditEntry> entries);
}
