package com.aariaudo.android.redditclient.presenters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.aariaudo.android.redditclient.ImageFsActivity;
import com.aariaudo.android.redditclient.constants.ExtraKeys;
import com.aariaudo.android.redditclient.data.HttpDataProvider;
import com.aariaudo.android.redditclient.model.RedditEntry;
import com.aariaudo.android.redditclient.views.EntryListView;

import java.util.ArrayList;

public class EntryListPresenter
{
    private EntryListView entryListView;
    private AsyncTask<Void, Integer, ArrayList<RedditEntry>> entryLoadTask;

    public EntryListPresenter(EntryListView entryListView)
    {
        this.entryListView = entryListView;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        entryLoadTask = new EntryLoadTask();
        entryLoadTask.execute();
    }

    public void onStop()
    {
        if(entryLoadTask != null)
        {
            entryLoadTask.cancel(true);
        }
    }

    public void onThumbnailClicked(Context context, RedditEntry redditEntry)
    {
        if(redditEntry.getThumbnail() !=null && redditEntry.getThumbnail().contains("http"))
        {
            Intent intent = new Intent(context, ImageFsActivity.class);
            intent.putExtra(ExtraKeys.REDDIT_ENTRY, redditEntry);
            context.startActivity(intent);
        }
    }

    private class EntryLoadTask extends AsyncTask<Void, Integer, ArrayList<RedditEntry>>
    {
        @Override
        protected void onPreExecute()
        {
            entryListView.showProgress();
        }

        @Override
        protected ArrayList<RedditEntry> doInBackground(Void... params)
        {
            return HttpDataProvider.getInstance().getEntries();
        }

        @Override
        protected void onPostExecute(ArrayList<RedditEntry> entries)
        {
            entryListView.addItems(entries);
            entryListView.hideProgress();
        }
    }
}
