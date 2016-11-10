package com.aariaudo.android.redditclient.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aariaudo.android.redditclient.R;
import com.aariaudo.android.redditclient.model.RedditEntry;
import com.bumptech.glide.Glide;

public class EntryRvAdapter extends ArrayRvAdapter<RedditEntry>
{
    public static class EntryViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvEliTitle;
        TextView tvEliAuthor;
        TextView tvEliComments;
        ImageView ivEliTumbnail;

        public EntryViewHolder(View itemView)
        {
            super(itemView);
            tvEliTitle = (TextView) itemView.findViewById(R.id.tv_eli_title);
            tvEliAuthor = (TextView) itemView.findViewById(R.id.tv_eli_author);
            tvEliComments = (TextView) itemView.findViewById(R.id.tv_eli_comments);
            ivEliTumbnail = (ImageView) itemView.findViewById(R.id.iv_eli_tumbnail);
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
        entryViewHolder.tvEliAuthor.setText(entry.getAuthor());
        entryViewHolder.tvEliComments.setText(String.valueOf(entry.getComments()));
        Glide.with(entryViewHolder.ivEliTumbnail.getContext()).load(entry.getThumbnail()).centerCrop().into(entryViewHolder.ivEliTumbnail);
    }
}
