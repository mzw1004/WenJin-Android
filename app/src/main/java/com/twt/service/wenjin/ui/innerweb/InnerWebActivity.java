package com.twt.service.wenjin.ui.innerweb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.receiver.JPushNotiReceiver;
import com.twt.service.wenjin.support.StatusBarHelper;
import com.twt.service.wenjin.ui.setting.SettingsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/22.
 */
public class InnerWebActivity extends AppCompatActivity {

    private static final String PARAM_WEB_URL = "web_url";

    private String mUrl;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.wb_innerweb)
    WebView wbInnerweb;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, InnerWebActivity.class);
        intent.putExtra(PARAM_WEB_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_innerweb);
        ButterKnife.bind(this);

        StatusBarHelper.setStatusBar(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebSettings webSettings = wbInnerweb.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        wbInnerweb.requestFocus();
        wbInnerweb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        wbInnerweb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.indexOf("tel:") < 0) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pbLoading.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pbLoading.setVisibility(View.GONE);
                wbInnerweb.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });


        mUrl = getIntent().getStringExtra(PARAM_WEB_URL);
        wbInnerweb.loadUrl(mUrl);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(wbInnerweb.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){
            wbInnerweb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
