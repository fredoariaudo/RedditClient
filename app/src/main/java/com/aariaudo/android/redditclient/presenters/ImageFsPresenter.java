package com.aariaudo.android.redditclient.presenters;

import android.os.Bundle;

import com.aariaudo.android.redditclient.views.ImageFsView;

public class ImageFsPresenter
{
    private ImageFsView imageFsView;

    public ImageFsPresenter(ImageFsView imageFsView)
    {
        this.imageFsView = imageFsView;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        imageFsView.loadUrl();
    }

    public void onPageStarted()
    {
        imageFsView.showProgress();
    }

    public void onPageFinished()
    {
        imageFsView.hideProgress();
    }
}
