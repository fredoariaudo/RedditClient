package com.aariaudo.android.redditclient.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aariaudo.android.redditclient.R;
import com.aariaudo.android.redditclient.model.RedditEntry;
import com.aariaudo.android.redditclient.views.EntryListView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Date;

public class EntryRvAdapter extends ArrayRvAdapter<RedditEntry>
{
    private static final int VIEW_TYPE_ENTRY = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private static final int VIEW_TYPE_EMPTY = 2;

    private EntryListView entryListView;

    public class EntryViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvEliTitle;
        TextView tvEliAuthorDate;
        TextView tvEliComments;
        RoundedImageView ivEliThumbnail;

        public EntryViewHolder(View itemView, final EntryListView entryListView)
        {
            super(itemView);
            tvEliTitle = (TextView) itemView.findViewById(R.id.tv_eli_title);
            tvEliAuthorDate = (TextView) itemView.findViewById(R.id.tv_eli_author_date);
            tvEliComments = (TextView) itemView.findViewById(R.id.tv_eli_comments);
            ivEliThumbnail = (RoundedImageView) itemView.findViewById(R.id.iv_eli_thumbnail);
            ivEliThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    entryListView.onThumbnailClicked(getItems().get(getLayoutPosition()));
                }
            });
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder
    {
        public LoadingViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder
    {
        public EmptyViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    public EntryRvAdapter(EntryListView entryListView)
    {
        this.entryListView = entryListView;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(getItems().get(position).getTitle().equals(RedditEntry.REDDIT_ENTRY_EMPTY))
            return VIEW_TYPE_EMPTY;
        else if(getItems().get(position).getTitle().equals(RedditEntry.REDDIT_ENTRY_LOADING))
            return VIEW_TYPE_LOADING;
        else
            return VIEW_TYPE_ENTRY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(viewType == VIEW_TYPE_ENTRY)
        {
            View itemView = inflater.inflate(R.layout.entry_list_item, parent, false);
            return new EntryViewHolder(itemView, entryListView);
        }
        else if(viewType == VIEW_TYPE_LOADING)
        {
            View itemView = inflater.inflate(R.layout.entry_list_loading_item, parent, false);
            return new LoadingViewHolder(itemView);
        }
        else
        {
            View itemView = inflater.inflate(R.layout.entry_list_empty_item, parent, false);
            return new EmptyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof EntryViewHolder)
        {
            RedditEntry entry = getItems().get(position);
            String dateString = DateUtils.getRelativeTimeSpanString(entry.getDate() * 1000, new Date().getTime(), DateUtils.SECOND_IN_MILLIS).toString();

            EntryViewHolder entryViewHolder = (EntryViewHolder) holder;
            entryViewHolder.tvEliTitle.setText(entry.getTitle());
            entryViewHolder.tvEliAuthorDate.setText(String.format(entryViewHolder.tvEliAuthorDate.getContext().getResources().getString(R.string.eli_author_date), entry.getAuthor(), dateString));
            entryViewHolder.tvEliComments.setText(String.valueOf(entry.getComments()));
            Glide.with(entryViewHolder.ivEliThumbnail.getContext()).load(entry.getThumbnail()).centerCrop().error(R.drawable.entry_default).into(entryViewHolder.ivEliThumbnail);
        }
    }
}
