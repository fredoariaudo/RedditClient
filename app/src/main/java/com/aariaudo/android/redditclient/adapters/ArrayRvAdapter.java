package com.aariaudo.android.redditclient.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public abstract class ArrayRvAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<T> items = new ArrayList<T>();

    public void addAll(ArrayList<T> items)
    {
        this.items.addAll(items);
        notifyItemRangeInserted(this.items.size(), items.size());
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public ArrayList<T> getItems()
    {
        return items;
    }
}
