package com.aariaudo.android.redditclient.model;

import java.io.Serializable;

public class RedditEntry implements Serializable
{
    public static final String REDDIT_ENTRY_LOADING = "loading";
    public static final String REDDIT_ENTRY_EMPTY = "empty";

    private String name;
    private String title;
    private String author;
    private long date;
    private int comments;
    private String thumbnail;
    private String url;

    public RedditEntry()
    {
    }

    public RedditEntry(String title)
    {
        this.title = title;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public long getDate()
    {
        return date;
    }

    public void setDate(long date)
    {
        this.date = date;
    }

    public int getComments()
    {
        return comments;
    }

    public void setComments(int comments)
    {
        this.comments = comments;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
