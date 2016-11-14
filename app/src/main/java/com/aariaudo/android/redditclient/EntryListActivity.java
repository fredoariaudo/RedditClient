package com.aariaudo.android.redditclient;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.aariaudo.android.redditclient.adapters.EntryRvAdapter;
import com.aariaudo.android.redditclient.components.DividerItemDecoration;
import com.aariaudo.android.redditclient.constants.ExtraKeys;
import com.aariaudo.android.redditclient.model.RedditEntry;
import com.aariaudo.android.redditclient.presenters.EntryListPresenter;
import com.aariaudo.android.redditclient.views.EntryListView;

import java.util.ArrayList;

public class EntryListActivity extends AppCompatActivity implements EntryListView
{
    private SwipeRefreshLayout srlEntryList;
    private ProgressBar pbEntryList;
    private EntryRvAdapter adapter;

    private EntryListPresenter entryListPresenter;
    private boolean loading = false;
    private boolean refreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        srlEntryList = (SwipeRefreshLayout) findViewById(R.id.srl_entry_list);
        srlEntryList.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        srlEntryList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                refreshing = true;
                entryListPresenter.onRefreshList();
            }
        });

        pbEntryList = (ProgressBar) findViewById(R.id.pb_entry_list);

        RecyclerView rvEntryList = (RecyclerView) findViewById(R.id.rv_entry_list);
        rvEntryList.setLayoutManager(new LinearLayoutManager(this));
        rvEntryList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rvEntryList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                entryListPresenter.onEntryListScroll(recyclerView, dy, loading);
            }
        });
        rvEntryList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return refreshing;
            }
        });

        adapter = new EntryRvAdapter(this);
        rvEntryList.setAdapter(adapter);

        entryListPresenter = new EntryListPresenter(this);
        entryListPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        entryListPresenter.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ExtraKeys.REDDIT_ENTRIES, adapter.getItems());
    }

    @Override
    public void showProgress()
    {
        pbEntryList.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        pbEntryList.setVisibility(View.INVISIBLE);
    }

    @Override
    public void enableSwipeGesture()
    {
        srlEntryList.setEnabled(true);
    }

    @Override
    public void disableSwipeGesture()
    {
        srlEntryList.setEnabled(false);
    }

    @Override
    public void addItems(ArrayList<RedditEntry> entries)
    {
        if(adapter.getItems().size() > 0)
        {
            adapter.remove(adapter.getItems().size() - 1);
            adapter.addAll(entries);
        }
        else
        {
            adapter.notifyDataSetChanged();

            if(entries.size() <= 0)
                adapter.add(new RedditEntry(RedditEntry.REDDIT_ENTRY_EMPTY));
            else
                adapter.addAll(entries);
        }

        srlEntryList.setRefreshing(false);
        loading = false;
        refreshing = false;
    }

    @Override
    public void refreshItems()
    {
        adapter.clear(false);
        entryListPresenter.loadEntries(null, false);
    }

    @Override
    public void loadMore()
    {
        new Handler().post(new Runnable() {
            @Override
            public void run()
            {
                RedditEntry redditEntry = adapter.getItems().get(adapter.getItemCount() - 1);
                adapter.add(new RedditEntry(RedditEntry.REDDIT_ENTRY_LOADING));
                loading = true;
                entryListPresenter.loadEntries(redditEntry.getName(), false);
            }
        });
    }

    @Override
    public void onThumbnailClicked(RedditEntry redditEntry)
    {
        entryListPresenter.onThumbnailClicked(this, redditEntry);
    }
}
