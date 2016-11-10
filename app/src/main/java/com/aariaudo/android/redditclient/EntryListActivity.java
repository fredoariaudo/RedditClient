package com.aariaudo.android.redditclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class EntryListActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        RecyclerView rvEntryList = (RecyclerView) findViewById(R.id.rv_entry_list);
        rvEntryList.setLayoutManager(new LinearLayoutManager(this));
    }
}
