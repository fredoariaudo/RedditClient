package com.aariaudo.android.redditclient.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aariaudo.android.redditclient.R;
import com.aariaudo.android.redditclient.model.RedditEntry;

public class EntryRvAdapter extends ArrayRvAdapter<RedditEntry>
{
    public static class EntryViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvEliTitle;

        public EntryViewHolder(View itemView)
        {
            super(itemView);
            tvEliTitle = (TextView) itemView.findViewById(R.id.tv_eli_title);
        }
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.entry_list_item, parent, false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        RedditEntry entry = getItems().get(position);
        EntryViewHolder entryViewHolder = (EntryViewHolder) holder;
        entryViewHolder.tvEliTitle.setText(entry.getTitle());
    }
}
