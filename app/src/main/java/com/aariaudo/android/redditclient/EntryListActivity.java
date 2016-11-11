package com.aariaudo.android.redditclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.aariaudo.android.redditclient.adapters.EntryRvAdapter;
import com.aariaudo.android.redditclient.components.DividerItemDecoration;
import com.aariaudo.android.redditclient.model.RedditEntry;
import com.aariaudo.android.redditclient.presenters.EntryListPresenter;
import com.aariaudo.android.redditclient.views.EntryListView;

import java.util.ArrayList;

public class EntryListActivity extends AppCompatActivity implements EntryListView
{
    private ProgressBar pbEntryList;
    private EntryRvAdapter adapter;

    private EntryListPresenter entryListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        pbEntryList = (ProgressBar) findViewById(R.id.pb_entry_list);

        RecyclerView rvEntryList = (RecyclerView) findViewById(R.id.rv_entry_list);
        rvEntryList.setLayoutManager(new LinearLayoutManager(this));
        rvEntryList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

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
    public void addItems(ArrayList<RedditEntry> entries)
    {
        adapter.addAll(entries);
    }

    @Override
    public void onThumbnailClicked(RedditEntry redditEntry)
    {
        entryListPresenter.onThumbnailClicked(this, redditEntry);
    }
}
