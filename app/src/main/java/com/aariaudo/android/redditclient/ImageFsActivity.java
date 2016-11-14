package com.aariaudo.android.redditclient;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aariaudo.android.redditclient.constants.ExtraKeys;
import com.aariaudo.android.redditclient.model.RedditEntry;
import com.aariaudo.android.redditclient.presenters.ImageFsPresenter;
import com.aariaudo.android.redditclient.views.ImageFsView;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ImageFsActivity extends AppCompatActivity implements ImageFsView
{
    private MaterialProgressBar pbImageFs;
    private WebView wbImageView;
    private RedditEntry redditEntry;

    private ImageFsPresenter imageFsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fs);

        redditEntry = (RedditEntry) getIntent().getSerializableExtra(ExtraKeys.REDDIT_ENTRY);

        pbImageFs = (MaterialProgressBar) findViewById(R.id.pb_image_fs);

        wbImageView = (WebView) findViewById(R.id.wb_image_view);
        WebSettings webSettings = wbImageView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbImageView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                imageFsPresenter.onPageStarted();
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                imageFsPresenter.onPageFinished();
            }
        });

        imageFsPresenter = new ImageFsPresenter(this);
        imageFsPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void showProgress()
    {
        pbImageFs.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        pbImageFs.setVisibility(View.GONE);
    }

    @Override
    public void loadUrl()
    {
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            wbImageView.loadUrl(Html.fromHtml(redditEntry.getUrl(), Html.FROM_HTML_MODE_LEGACY).toString());
        else
            wbImageView.loadUrl(Html.fromHtml(redditEntry.getUrl()).toString());
    }
}
