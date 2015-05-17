package com.twt.service.wenjin.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.twt.service.wenjin.WenJinApp;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.support.StatusBarHelper;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by M on 2015/3/20.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ObjectGraph mActivityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityGraph = ((WenJinApp) getApplication()).createScopedGraph(getModules().toArray());
        mActivityGraph.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ApiClient.getInstance().cancelRequests(this, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityGraph = null;
    }

    protected abstract List<Object> getModules();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarHelper.setStatusBar(this);
    }
}
