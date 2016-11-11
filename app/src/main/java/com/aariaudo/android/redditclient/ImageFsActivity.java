package com.aariaudo.android.redditclient;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aariaudo.android.redditclient.constants.ExtraKeys;
import com.aariaudo.android.redditclient.model.RedditEntry;
import com.aariaudo.android.redditclient.presenters.ImageFsPresenter;
import com.aariaudo.android.redditclient.views.ImageFsView;

public class ImageFsActivity extends AppCompatActivity implements ImageFsView
{
    private WebView wbImageView;
    private RedditEntry redditEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fs);

        redditEntry = (RedditEntry) getIntent().getSerializableExtra(ExtraKeys.REDDIT_ENTRY);
        wbImageView = (WebView) findViewById(R.id.wb_image_view);

        ImageFsPresenter imageFsPresenter = new ImageFsPresenter(this);
        imageFsPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void loadUrl()
    {
        WebSettings webSettings = wbImageView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbImageView.setWebViewClient(new WebViewClient());

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            wbImageView.loadUrl(Html.fromHtml(redditEntry.getUrl(), Html.FROM_HTML_MODE_LEGACY).toString());
        else
            wbImageView.loadUrl(Html.fromHtml(redditEntry.getUrl()).toString());
    }
}
