package com.twt.service.wenjin.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.twt.service.wenjin.WenJinApp;
import com.twt.service.wenjin.api.ApiClient;

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
        mActivityGraph = ((WenJinApp) getApplication()).createScopedGraph(getModlues().toArray());
        mActivityGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityGraph = null;
        ApiClient.getInstance().cancelRequests(this, false);
    }

    protected abstract List<Object> getModlues();
}
