package com.twt.service.wenjin.ui.login.greenchannel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.support.StatusBarHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by RexSun on 15/7/18.
 */
public class GreenChannelActivity extends AppCompatActivity implements GreenChannelView {
    private GreenChannelPresenter greenChannelPresenter;

    @Bind(R.id.pb_green_channel_loading)
    ProgressBar pbGreenChannelLoading;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.wv_green_channel)
    WebView wvGreenChannel;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, GreenChannelActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_channel);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(ResourceHelper.getString(R.string.login_signin));
        greenChannelPresenter = new GreenChannelPresenter(this);
        greenChannelPresenter.loadGreenChannel();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarHelper.setStatusBar(this);
    }

    @Override
    public void showProgressBar() {
        pbGreenChannelLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        pbGreenChannelLoading.setVisibility(View.GONE);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWebView(String greenChannelUrl) {
        wvGreenChannel.getSettings().setJavaScriptEnabled(true);
        wvGreenChannel.loadUrl(greenChannelUrl);
        wvGreenChannel.setWebViewClient(new GreenChannelWebViewClient());
        wvGreenChannel.setWebChromeClient(new WebChromeClient());
    }

    private class GreenChannelWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e("pagestart", "pagestart");
            pbGreenChannelLoading.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            pbGreenChannelLoading.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvGreenChannel.canGoBack()) {
            wvGreenChannel.goBack();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        StatusBarHelper.setStatusBar(this);
    }
}
