package com.aariaudo.android.redditclient.presenters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        if(savedInstanceState == null)
        {
            loadEntries(null, true);
        }
        else
        {
            ArrayList<RedditEntry> entries = (ArrayList<RedditEntry>) savedInstanceState.getSerializable(ExtraKeys.REDDIT_ENTRIES);
            entryListView.addItems(entries);
        }
    }

    public void onStop()
    {
        if(entryLoadTask != null)
        {
            entryLoadTask.cancel(true);
        }
    }

    public void loadEntries(String lastEntryName, boolean showLoading)
    {
        entryLoadTask = new EntryLoadTask(lastEntryName, showLoading);
        entryLoadTask.execute();
    }

    public void onRefreshList()
    {
        entryListView.refreshItems();
    }

    public void onEntryListScroll(RecyclerView recyclerView, int dy, boolean loading)
    {
        if(dy > 0)
        {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = linearLayoutManager.getItemCount();

            if(!loading && totalItemCount < EntryListView.TOTAL_ITEMS_TO_SHOW)
            {
                int visibleItemCount = linearLayoutManager.getChildCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if((visibleItemCount + pastVisibleItems) >= totalItemCount)
                {
                    entryListView.loadMore();
                }
            }
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
        private String lastEntryName;
        private boolean showLoading;

        public EntryLoadTask(String lastEntryName, boolean showLoading)
        {
            this.lastEntryName = lastEntryName;
            this.showLoading = showLoading;
        }

        @Override
        protected void onPreExecute()
        {
            if(showLoading)
            {
                entryListView.disableSwipeGesture();
                entryListView.showProgress();
            }
        }

        @Override
        protected ArrayList<RedditEntry> doInBackground(Void... params)
        {
            return HttpDataProvider.getInstance().getEntries(lastEntryName);
        }

        @Override
        protected void onPostExecute(ArrayList<RedditEntry> entries)
        {
            entryListView.addItems(entries);

            if(showLoading)
            {
                entryListView.hideProgress();
                entryListView.enableSwipeGesture();
            }
        }
    }
}
